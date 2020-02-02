package com.zatsepinvl.activity.play.dictionary

import android.content.Context
import android.content.res.AssetManager
import com.zatsepinvl.activity.play.core.Dictionary
import com.zatsepinvl.activity.play.core.Word
import com.zatsepinvl.activity.play.core.WordType
import com.zatsepinvl.activity.play.settings.ActivityPlayPreference
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*
import javax.inject.Inject


interface DictionaryService {
    fun loadDictionary(): Dictionary
    fun loadDictionary(language: SupportedLanguage): Dictionary
    fun getDefaultLanguage(): SupportedLanguage
}

class DictionaryServiceImpl @Inject constructor(
    private val context: Context
) : DictionaryService {

    private var dictionaryCache: Dictionary? = null
    private var dictionaryCachedLanguage: SupportedLanguage? = null

    override fun loadDictionary(): Dictionary {
        val language = getLanguage()
        return loadDictionary(language)
    }

    override fun loadDictionary(language: SupportedLanguage): Dictionary {
        if (dictionaryCache == null || language != dictionaryCachedLanguage) {
            dictionaryCachedLanguage = language
            dictionaryCache = context.assets.dictionary(
                WordFile("words/words_${language.tag}.txt")
            )
        }
        return dictionaryCache!!
    }

    override fun getDefaultLanguage(): SupportedLanguage {
        return SupportedLanguage.values().toList().find {
            it.tag == Locale.getDefault().language
        } ?: SupportedLanguage.EN
    }

    private fun getLanguage(): SupportedLanguage {
        return ActivityPlayPreference.getActivityPlayPreferences(context).dictionaryLanguage
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