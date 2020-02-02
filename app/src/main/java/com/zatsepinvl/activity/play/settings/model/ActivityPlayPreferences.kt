package com.zatsepinvl.activity.play.settings.model

import com.zatsepinvl.activity.play.core.model.GameAction
import com.zatsepinvl.activity.play.dictionary.SupportedLanguage

data class ActivityPlayPreferences(
    val dictionaryLanguage: SupportedLanguage,
    val fineForSkipping: Boolean,
    val roundTimeSeconds: Int,
    val maxScore: Int,
    val enabledActions: Set<GameAction>
)