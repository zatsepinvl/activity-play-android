package com.zatsepinvl.activity.play.core

import com.zatsepinvl.activity.play.core.WordType.*
import java.util.*

enum class WordType {
    NOUN, ADJECTIVE, VERB
}

data class Word(
    val value: String,
    val type: WordType
)

const val DEFAULT_RETRY_COUNT = 1000

class Dictionary(
    val language: String,
    private val words: List<Word>,
    private val defaultRetryCount: Int = DEFAULT_RETRY_COUNT
) {
    private var random = Random()

    fun getRandomWord(
        excludeWords: Set<Word> = setOf(),
        retryCount: Int = defaultRetryCount,
        wordTypes: Set<WordType> = values().toSet()
    ): Word {
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

fun noun(word: String) = Word(word, NOUN)
fun verb(word: String) = Word(word, VERB)
fun adjective(word: String) = Word(word, ADJECTIVE)