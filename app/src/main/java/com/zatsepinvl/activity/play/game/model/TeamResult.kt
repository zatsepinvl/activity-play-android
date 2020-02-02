package com.zatsepinvl.activity.play.game.model

import com.zatsepinvl.activity.play.team.model.Team

data class TeamResult(
    val team: Team,
    val totalScore: Int,
    val winner: Boolean
)