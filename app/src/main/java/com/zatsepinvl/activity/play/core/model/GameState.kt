package com.zatsepinvl.activity.play.core.model

data class GameState(
    val completedTasks: MutableList<CompletedTask>,
    val finished: Boolean,
    val currentRoundIndex: Int,
    val currentTeamIndex: Int,
    val currentGameAction: GameAction?
)