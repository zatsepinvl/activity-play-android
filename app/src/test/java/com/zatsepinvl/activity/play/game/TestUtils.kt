import com.zatsepinvl.activity.play.core.*

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
    )
): ActivityGame {
    return ActivityGame(settings, dictionary)
}