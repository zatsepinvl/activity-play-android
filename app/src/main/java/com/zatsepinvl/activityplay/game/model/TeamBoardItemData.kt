package com.zatsepinvl.activityplay.game.model

import com.zatsepinvl.activityplay.team.model.Team

data class TeamBoardItemData(
    val team: Team,
    val totalScore: Int,
    val isCurrentTeam: Boolean
)