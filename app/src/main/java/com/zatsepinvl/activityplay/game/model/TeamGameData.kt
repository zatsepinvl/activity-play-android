package com.zatsepinvl.activityplay.game.model

import com.zatsepinvl.activityplay.team.model.Team

data class TeamGameData(
    val team: Team,
    val totalScore: Int,
    val isCurrentTeam: Boolean,
    val winner: Boolean
)