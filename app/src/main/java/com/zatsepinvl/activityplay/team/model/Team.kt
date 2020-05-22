package com.zatsepinvl.activityplay.team.model

import com.zatsepinvl.activityplay.color.ColorId

data class Team(
    val id: String,
    val index: Int,
    val name: String,
    val colorId: ColorId,
    val color: Int
)