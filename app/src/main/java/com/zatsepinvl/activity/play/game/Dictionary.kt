package com.zatsepinvl.activity.play.game

import com.zatsepinvl.activity.play.game.WordType.*
import java.io.InputStream
import java.util.*

enum class WordType {
    NOUN, ADJECTIVE, VERB
}

data class Word(
    val value: String,
    val type: WordType
)

const val DEAFULT_RETRY_COUNT = 1000

class Dictionary(
    private val wordMap: Map<String, List<Word>>,
    private val defaultRetryCount: Int = DEAFULT_RETRY_COUNT
) {
    private var random = Random()

    fun isLanguageSupported(lang: String): Boolean {
        return wordMap[lang]?.isNotEmpty() ?: false
    }

    fun getRandomWord(
        lang: String,
        excludeWords: Set<Word> = setOf(),
        retryCount: Int = defaultRetryCount,
        wordTypes: Set<WordType> = values().toSet()
    ): Word {
        require(isLanguageSupported(lang)) { "Language $lang is not supported." }
        val words = wordMap[lang]!!
        val wordByTypes = words.filter { wordTypes.contains(it.type) }
        val getWord = { wordByTypes[random.nextInt(words.size)] }
        for (i in 1..retryCount) {
            val word = getWord()
            if (!excludeWords.contains(word)) {
                return word
            }
        }
        return getWord()
    }
}

fun createDictionary(
    wordPairs: List<Pair<String, Word>>,
    retryCount: Int = DEAFULT_RETRY_COUNT
): Dictionary {
    return Dictionary(
        wordPairs.groupBy({ it.first }, { it.second }),
        retryCount
    )
}

fun noun(word: String) = Word(word, NOUN)
fun verb(word: String) = Word(word, VERB)
fun adjective(word: String) = Word(word, ADJECTIVE)