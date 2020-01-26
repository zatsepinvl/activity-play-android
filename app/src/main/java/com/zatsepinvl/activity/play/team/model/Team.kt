package com.zatsepinvl.activity.play.team.model

import com.zatsepinvl.activity.play.color.ColorId

data class Team(
    val id: String,
    val index: Int,
    val name: String,
    val colorId: ColorId,
    val color: Int
)