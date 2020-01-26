package com.zatsepinvl.activity.play.core.model

import com.zatsepinvl.activity.play.core.Word

data class GameTask(
    val teamIndex: Int,
    val roundIndex: Int,
    val action: GameAction,
    val word: Word
) {
    val frameId = "$roundIndex:$teamIndex"
}