package com.zatsepinvl.activityplay.core

import com.zatsepinvl.activityplay.core.model.CompletedTask


fun ActivityGame.getCurrentRoundCompletedTasks(): List<CompletedTask> {
    return getTeamCompletedTasks(currentTeamIndex)
        .filter { it.task.roundIndex == currentRoundIndex }
}

fun List<CompletedTask>.totalScore(): Int {
    return this.sumBy { it.result.score }
}

fun List<CompletedTask>.totalScoreForRound(roundIndex: Int): Int {
    return this.filter { it.task.roundIndex == roundIndex }
        .sumBy { it.result.score }
}

fun List<CompletedTask>.totalScoreForLastRound(): Int {
    val lastRound: Int = this.map { it.task.roundIndex }.max() ?: 0
    return totalScoreForRound(lastRound)
}