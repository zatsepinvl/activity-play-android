package com.zatsepinvl.activity.play

import android.content.res.AssetManager
import com.zatsepinvl.activity.play.core.Dictionary
import com.zatsepinvl.activity.play.core.Word
import com.zatsepinvl.activity.play.core.WordType
import java.io.InputStream
import java.nio.charset.StandardCharsets

data class WordFile(val file: String) {
    val lang = file.run {
        substring(indexOf('_') + 1, indexOf('.'))
    }
}

fun AssetManager.getSupportedLanguages(): Set<WordFile> {
    return list("words")!!.filter { it.startsWith("words") }
        .map { file -> WordFile(file) }
        .toSet()
}

fun AssetManager.dictionary(wordFile: WordFile): Dictionary {
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