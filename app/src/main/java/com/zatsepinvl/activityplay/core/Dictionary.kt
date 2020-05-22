package com.zatsepinvl.activityplay.core

import com.zatsepinvl.activityplay.core.WordType.*
import java.util.*

enum class WordType {
    NOUN, ADJECTIVE, VERB
}

data class Word(
    val value: String,
    val type: WordType
)

class Dictionary(
    val language: String,
    private val words: List<Word>
) {
    private var random = Random()

    fun getRandomWord(
        excludeWords: Set<Word> = setOf(),
        wordTypes: Set<WordType> = setOf(NOUN)
    ): Word {
        val filteredWords = words
            .filter { wordTypes.contains(it.type) }
            .filter { !excludeWords.contains(it) }
        if (filteredWords.isEmpty()) {
            throw NoWordsFoundException("No words found matching request.")
        }
        return filteredWords[random.nextInt(filteredWords.size)]
    }
}

fun noun(word: String) = Word(word, NOUN)
fun verb(word: String) = Word(word, VERB)
fun adjective(word: String) = Word(word, ADJECTIVE)

class NoWordsFoundException(message: String?) : Exception(message)