package com.zatsepinvl.activity.play.core

import com.zatsepinvl.activity.play.core.WordType.NOUN
import createTestDictionary
import org.junit.Assert.assertEquals
import org.junit.Test

class DictionaryTest {

    @Test
    fun get_random_word() {
        val expectedWord = noun("word")
        val anotherWord = noun("another")
        val dictionary = createTestDictionary(expectedWord, anotherWord)

        val actualWord = dictionary.getRandomWord(excludeWords = setOf(anotherWord))
        assertEquals(expectedWord, actualWord)
    }

    @Test
    fun get_random_word_loaded() {
        val expectedWord = "dictionary"
        val dictionary = createTestDictionary(
            (1..10000).map { noun(expectedWord) }
        )

        val actualWord = dictionary.getRandomWord()
        assertEquals(expectedWord, actualWord.value)
    }

    @Test
    fun get_random_word_by_type() {
        val expectedWord = noun("word")
        val anotherWord = verb("another")
        val dictionary = createTestDictionary(expectedWord, anotherWord)

        val actualWord = dictionary.getRandomWord(wordTypes = setOf(NOUN))
        assertEquals(expectedWord, actualWord)
    }


}




