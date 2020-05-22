package com.zatsepinvl.activityplay.game.model

import com.zatsepinvl.activityplay.team.model.Team

data class TeamResult(
    val team: Team,
    val position: Int,
    val totalScore: Int,
    val winner: Boolean
)