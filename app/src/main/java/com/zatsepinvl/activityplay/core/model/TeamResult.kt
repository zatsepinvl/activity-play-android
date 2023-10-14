package com.zatsepinvl.activityplay.core.model

data class TeamResult(
    val tasks: List<CompletedTask>
) {
    val score = tasks.sumOf { it.result.score }.coerceAtLeast(0)
}