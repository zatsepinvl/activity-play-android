package com.zatsepinvl.activityplay.core.model

/**
 * Nullability is used in order to keep backward compatibility
 */
data class GameState(
    val completedTasks: List<CompletedTask>,
    val teamScores: Map<Int, Int>?,
    val finished: Boolean,
    val currentRoundIndex: Int,
    val currentTeamIndex: Int,
    val currentGameAction: GameAction?,
    val currentTask: GameTask?,
    val roundIsPlaying: Boolean?
)