import com.zatsepinvl.activity.play.core.ActivityGame
import com.zatsepinvl.activity.play.core.Dictionary
import com.zatsepinvl.activity.play.core.Word
import com.zatsepinvl.activity.play.core.model.GameSettings
import com.zatsepinvl.activity.play.core.model.GameState
import com.zatsepinvl.activity.play.core.noun

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