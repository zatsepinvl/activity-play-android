package com.zatsepinvl.activityplay.core

import com.zatsepinvl.activityplay.core.WordType.*
import java.util.*
import kotlin.math.max

enum class WordType {
    NOUN, ADJECTIVE, VERB
}

enum class WordDifficulty: Comparable<WordDifficulty> {
    LOW, MEDIUM, HIGH;

    companion object {
        // https://github.com/zatsepinvl/activity-game-lang-values/blob/master/README.md#pay-attantion-how-to-translate-words_enxml
        fun fromActionRange(draw: Int, show: Int, speak: Int): WordDifficulty {
            val rate = max(max(draw, show), speak)
            return when (rate) {
                2 -> LOW
                in 3..4 -> MEDIUM
                else -> HIGH
            }
        }
    }
}

data class Word(
    val value: String,
    val type: WordType,
    val difficulty: WordDifficulty
)

class Dictionary(val language: String, words: List<Word>) {

    companion object {
        private const val MAX_ATTEMPTS = 1000
    }

    private val words: List<Word>
    private var random = Random()

    init {
        this.words = words.sortedBy { it.difficulty }
    }

    fun getRandomWord(
        excludeWords: Set<Word> = setOf(),
        wordTypes: Set<WordType> = setOf(NOUN)
    ): Word {
        val filteredWords = words.filter { wordTypes.contains(it.type) }
        if (filteredWords.isEmpty()) {
            throw NoWordsFoundException("No words found matching request.")
        }
        return getRandomWordsWithAttempts(filteredWords, excludeWords)
    }

    private fun getRandomWordsWithAttempts(
        filteredWords: List<Word>,
        excludeWords: Set<Word>,
        attempt: Int = 0
    ): Word {
        val index = random.nextInt(filteredWords.size)
        val word = filteredWords.randomGaussian(random)
        if (excludeWords.contains(word)) {
            if (attempt >= MAX_ATTEMPTS) {
                return word
            }
            return getRandomWordsWithAttempts(filteredWords, excludeWords, attempt + 1)
        } else {
            return word
        }
    }
}

fun <E> Collection<E>.randomGaussian(rnd: Random): E {
    // The random.nextGaussian() generates random numbers by following the standard Gaussian distribution,
    // also known as the normal distribution, where the mean is 0 and standard deviation is 1.
    // This method generates numbers so that about 68% of the time, they fall within -1 and 1,
    // about 95% of the time they fall within -2 and 2, and about 99.7% of the time they fall within -3 and 3.
    // By dividing the value by 3, we are effectively reducing the standard deviation so that 99.7% of the time,
    // the value will be within -1 and 1. Which means we bring +- 99.7% variation
    // (3-sigma variation in standard normal distribution) to +- 1.
    val gaussian = rnd.nextGaussian() / 3
    val index = (gaussian * size / 2 + size / 2).toInt()
        .coerceAtLeast(0)
        .coerceAtMost(size - 1)
    return elementAt(index)
}

class NoWordsFoundException(message: String?) : Exception(message)