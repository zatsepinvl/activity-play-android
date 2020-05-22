package com.zatsepinvl.activityplay.settings.model

import com.zatsepinvl.activityplay.core.model.GameAction
import com.zatsepinvl.activityplay.language.SupportedLanguage

data class ActivityPlayPreferences(
    val dictionaryLanguage: SupportedLanguage,
    val fineForSkipping: Boolean,
    val roundTimeSeconds: Int,
    val maxScore: Int,
    val soundsEnabled: Boolean,
    val vibrationEnabled: Boolean,
    val enabledActions: Set<GameAction>
)