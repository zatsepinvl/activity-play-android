package com.zatsepinvl.activityplay.dictionary

import android.content.Context
import android.content.res.AssetManager
import com.zatsepinvl.activityplay.core.Dictionary
import com.zatsepinvl.activityplay.core.Word
import com.zatsepinvl.activityplay.core.WordType
import com.zatsepinvl.activityplay.language.SupportedLanguage
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


interface DictionaryHolder {
    fun loadDictionary(language: SupportedLanguage): Dictionary
    fun getDictionary(): Dictionary
    fun getDefaultLanguage(): SupportedLanguage
}

@Singleton
class DictionaryHolderImpl @Inject constructor(
    private val context: Context
) : DictionaryHolder {
    private var dictionary: Dictionary? = null
    private var dictionaryLanguage: SupportedLanguage? = null

    override fun loadDictionary(language: SupportedLanguage): Dictionary {
        if (dictionary == null || language != dictionaryLanguage) {
            dictionaryLanguage = language
            dictionary = context.assets.dictionary(
                WordFile("words/words_${language.tag}.txt")
            )
        }
        return dictionary!!
    }

    override fun getDictionary(): Dictionary {
        checkNotNull(dictionary) { "Dictionary is null, load it first." }
        return dictionary!!
    }

    override fun getDefaultLanguage(): SupportedLanguage {
        return SupportedLanguage.values().toList().find {
            it.tag == Locale.getDefault().language
        } ?: SupportedLanguage.EN
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