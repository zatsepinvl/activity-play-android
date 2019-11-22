package com.zatsepinvl.activity.play.game

import com.zatsepinvl.activity.play.game.WordType.NOUN
import org.junit.Assert.*
import org.junit.Test

class DictionaryTest {

    @Test
    fun get_random_word() {
        val expectedWord = noun("word")
        val anotherWord = noun("another")
        val dictionary = createDictionary(
            listOf(
                "en" to expectedWord,
                "en" to anotherWord
            )
        )

        val actualWord = dictionary.getRandomWord(lang = "en", excludeWords = setOf(anotherWord))
        assertEquals(expectedWord, actualWord)
    }

    @Test
    fun get_random_word_loaded() {
        val expectedWord = "dictionary"
        val dictionary = createDictionary(
            (1..10000).map {
                "en" to noun(expectedWord)
            }.toList()
        )

        val actualWord = dictionary.getRandomWord("en")
        assertEquals(expectedWord, actualWord.value)
    }

    @Test
    fun get_random_word_by_type() {
        val expectedWord = noun("word")
        val anotherWord = verb("another")
        val dictionary = createDictionary(
            listOf(
                "en" to expectedWord,
                "en" to anotherWord
            )
        )

        val actualWord = dictionary.getRandomWord(lang = "en", wordTypes = setOf(NOUN))
        assertEquals(expectedWord, actualWord)
    }


}

