package com.zatsepinvl.activity.play.core.model

data class GameSettings(
    val teamCount: Int = 2,
    val pointsForDone: Int = 1,
    val pointsForFail: Int = 0,
    val maxScore: Int = 20,
    val actions: Set<GameAction> = setOf(
        GameAction.SAY,
        GameAction.DRAW,
        GameAction.SHOW
    )
)