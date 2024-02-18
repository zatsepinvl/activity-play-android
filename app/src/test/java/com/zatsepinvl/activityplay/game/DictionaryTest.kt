package com.zatsepinvl.activityplay.game

import com.zatsepinvl.activityplay.core.Word
import com.zatsepinvl.activityplay.core.WordDifficulty
import com.zatsepinvl.activityplay.core.WordDifficulty.HIGH
import com.zatsepinvl.activityplay.core.WordDifficulty.LOW
import com.zatsepinvl.activityplay.core.WordDifficulty.MEDIUM
import com.zatsepinvl.activityplay.core.WordType.NOUN
import createTestDictionary
import noun
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import verb

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

    @Test
    fun should_be_distributed_normally() {
        // Given
        val words = (1..10_000).map {
            Word(value = "word${it}", NOUN, WordDifficulty.entries.random())
        }
        val dictionary = createTestDictionary(words)

        // When
        val wordDist = mutableMapOf<WordDifficulty, Int>()
        repeat(words.size) {
            val word = dictionary.getRandomWord()
            wordDist.compute(word.difficulty) { _, count -> (count ?: 0) + 1 }
        }

        // Then
        println("Distribution: $wordDist")
        assertTrue(
            "MEDIUM words should be at least 2 times more than LOW",
            wordDist[MEDIUM]!! / wordDist[LOW]!! > 2
        )
        assertTrue(
            "MEDIUM words should be at least 2 times more than HIGH",
            wordDist[MEDIUM]!! / wordDist[HIGH]!! > 2
        )
    }

}




