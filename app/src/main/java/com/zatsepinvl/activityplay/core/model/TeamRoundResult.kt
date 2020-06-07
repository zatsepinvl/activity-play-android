package com.zatsepinvl.activityplay.core.model

data class TeamRoundResult(
    val tasks: List<CompletedTask>
) {
    val score = tasks.sumBy { it.result.score }.coerceAtLeast(0)
}