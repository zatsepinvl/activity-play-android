import com.zatsepinvl.activityplay.core.ActivityGame
import com.zatsepinvl.activityplay.core.Dictionary
import com.zatsepinvl.activityplay.core.Word
import com.zatsepinvl.activityplay.core.WordDifficulty
import com.zatsepinvl.activityplay.core.WordDifficulty.*
import com.zatsepinvl.activityplay.core.WordType.ADJECTIVE
import com.zatsepinvl.activityplay.core.WordType.NOUN
import com.zatsepinvl.activityplay.core.WordType.VERB
import com.zatsepinvl.activityplay.core.model.GameSettings
import com.zatsepinvl.activityplay.core.model.GameState

fun createTestDictionary(vararg words: Word): Dictionary {
    return createTestDictionary(words.toList())
}

fun createTestDictionary(words: List<Word>): Dictionary {
    return Dictionary("en", words)
}

fun createTestGame(
    settings: GameSettings = GameSettings(),
    dictionary: Dictionary = createTestDictionary(
        (1..100).map { noun("word#$it") }.toList()
    ),
    state: GameState? = null
): ActivityGame {
    return ActivityGame(settings, dictionary, state)
}

fun noun(word: String, difficulty: WordDifficulty = MEDIUM) = Word(word, NOUN, difficulty)
fun verb(word: String, difficulty: WordDifficulty = MEDIUM) = Word(word, VERB, difficulty)
fun adjective(word: String, difficulty: WordDifficulty = MEDIUM) = Word(word, ADJECTIVE, difficulty)