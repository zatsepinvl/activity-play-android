package com.zatsepinvl.activity.play.settings.model

enum class ActivityPlayPreferenceKey(
    val key: String
) {
    DICTIONARY_LANGUAGE("dictionaryLanguage"),
    FINE_FOR_SKIPPING("fineForSkipping"),
    ROUND_TIME_SECONDS("roundTimeSeconds"),
    MAX_SCORE("maxScore");
}