package com.zatsepinvl.activityplay.settings.model

enum class ActivityPlayPreferenceKey(
    val key: String
) {
    DICTIONARY_LANGUAGE("dictionaryLanguage"),
    FINE_FOR_SKIPPING("fineForSkipping"),
    ROUND_TIME_SECONDS("roundTimeSeconds"),
    MAX_SCORE("maxScore"),
    SOUND_EFFECTS("soundEffects"),
    VIBRATION("vibration")
}