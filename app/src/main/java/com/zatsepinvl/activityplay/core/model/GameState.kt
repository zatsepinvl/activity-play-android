package com.zatsepinvl.activityplay.core.model

/**
 * Nullability is used in order to keep backward compatibility
 */
data class GameState(
    val completedTasks: List<CompletedTask>,
    val teamScores: Map<String, Int>,
    val finished: Boolean,
    val currentRoundIndex: Int,
    val currentTeamIndex: Int,
    val currentGameAction: GameAction?,
    val currentTask: GameTask?,
    val roundIsPlaying: Boolean
)

object GameStateFactory {
    fun defaultGameState(): GameState {
        return GameState(
            completedTasks = listOf(),
            teamScores = mapOf(),
            finished = false,
            currentRoundIndex = 0,
            currentTeamIndex = 0,
            currentGameAction = null,
            currentTask = null,
            roundIsPlaying = false
        )
    }
}