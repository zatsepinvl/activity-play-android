import com.zatsepinvl.activityplay.core.ActivityGame
import com.zatsepinvl.activityplay.core.Dictionary
import com.zatsepinvl.activityplay.core.Word
import com.zatsepinvl.activityplay.core.model.GameSettings
import com.zatsepinvl.activityplay.core.model.GameState
import com.zatsepinvl.activityplay.core.noun

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