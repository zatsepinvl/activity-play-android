package com.zatsepinvl.activityplay.settings.model

import com.zatsepinvl.activityplay.core.model.GameAction

enum class ActivityPlayPreferenceActionKey(
    val key: String,
    val action: GameAction
) {
    ACTION_SAY("actionSay", GameAction.SAY),
    ACTION_DRAW("actionDraw", GameAction.DRAW),
    ACTION_SHOW("actionShow", GameAction.SHOW),
}