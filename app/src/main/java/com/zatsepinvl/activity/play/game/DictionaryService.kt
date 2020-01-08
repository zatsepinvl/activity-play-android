package com.zatsepinvl.activity.play.game

import android.content.Context
import android.content.res.AssetManager
import com.zatsepinvl.activity.play.core.Dictionary
import com.zatsepinvl.activity.play.core.Word
import com.zatsepinvl.activity.play.core.WordType
import com.zatsepinvl.activity.play.settings.DictionarySettingsRepository
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*
import javax.inject.Inject


data class DictionarySettings(
    val language: SupportedLanguage = SupportedLanguage.EN
)

enum class SupportedLanguage {
    EN,
    RU;

    val tag = this.name.toLowerCase(Locale.ROOT)
}

interface DictionaryService {
    fun getDictionary(): Dictionary
    fun defaultSettings(): DictionarySettings
    fun saveSettings(settings: DictionarySettings)
    fun getSettings(): DictionarySettings
}

class DictionaryServiceImpl @Inject constructor(
    private val settingsRepository: DictionarySettingsRepository,
    private val context: Context
) : DictionaryService {

    private lateinit var dictionaryCache: Dictionary

    init {
        updateDictionary()
    }

    override fun getDictionary(): Dictionary {
        return dictionaryCache
    }

    private fun loadDictionary(): Dictionary {
        val settings = getSettings()
        return context.assets.dictionary(
            WordFile("words/words_${settings.language.tag}.txt")
        )
    }

    override fun defaultSettings(): DictionarySettings {
        val currentLang = Locale.getDefault().language
        val language = SupportedLanguage.values().toSet()
            .find { it.tag == currentLang }
            ?: SupportedLanguage.EN
        return DictionarySettings(language)
    }

    override fun saveSettings(settings: DictionarySettings) {
        settingsRepository.save(settings)
        updateDictionary()
    }

    override fun getSettings(): DictionarySettings {
        return settingsRepository.get() ?: defaultSettings()
    }

    private fun updateDictionary() {
        dictionaryCache = loadDictionary()
    }
}

data class WordFile(val file: String) {
    val lang = file.run {
        substring(indexOf('_') + 1, indexOf('.'))
    }
}

private fun AssetManager.getSupportedLanguages(): Set<WordFile> {
    return list("words")!!.filter { it.startsWith("words") }
        .map { file -> WordFile(file) }
        .toSet()
}

private fun AssetManager.dictionary(wordFile: WordFile): Dictionary {
    return Dictionary(
        wordFile.lang, getWords(open(wordFile.file))
    )
}

private fun getWords(inputStream: InputStream): List<Word> {
    return inputStream.bufferedReader(StandardCharsets.UTF_8)
        .useLines {
            it.map(::parseLine).toList()
        }
}

private fun parseLine(line: String): Word {
    val s = line.split("\t".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val wordValue = s[0]
    val wordTypeLetter = s[1][0]
    return Word(
        wordValue,
        when (wordTypeLetter) {
            'n' -> WordType.NOUN
            'v' -> WordType.VERB
            'a' -> WordType.ADJECTIVE
            else -> throw IllegalArgumentException("Unknown word type $wordTypeLetter")
        }
    )
}