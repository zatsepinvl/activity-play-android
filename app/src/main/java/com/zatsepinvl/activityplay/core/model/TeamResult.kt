package com.zatsepinvl.activityplay.core.model

data class TeamResult(
    val tasks: List<CompletedTask>
) {
    val score = tasks.sumBy { it.result.score }.coerceAtLeast(0)
}