package com.zatsepinvl.activityplay.core.model

import com.zatsepinvl.activityplay.core.Word

data class GameTask(
    val teamIndex: Int,
    val roundIndex: Int,
    val action: GameAction,
    val word: Word
) {
    val frameId = "$roundIndex:$teamIndex"
}