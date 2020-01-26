package com.zatsepinvl.activity.play.game.model

import com.zatsepinvl.activity.play.team.model.Team

data class TeamBoardItemData(
    val team: Team,
    val totalScore: Int,
    val isCurrentTeam: Boolean
)