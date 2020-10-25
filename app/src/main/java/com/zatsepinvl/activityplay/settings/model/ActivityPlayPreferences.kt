package com.zatsepinvl.activityplay.settings.model

import com.zatsepinvl.activityplay.core.model.GameAction

data class ActivityPlayPreferences(
    val dictionaryLanguage: String,
    val fineForSkipping: Boolean,
    val roundTimeSeconds: Int,
    val maxScore: Int,
    val soundsEnabled: Boolean,
    val vibrationEnabled: Boolean,
    val enabledActions: List<GameAction>
)