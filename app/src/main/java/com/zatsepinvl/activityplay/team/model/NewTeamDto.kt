package com.zatsepinvl.activityplay.team.model

import android.content.Intent
import com.zatsepinvl.activityplay.color.ColorId

data class NewTeamDto(
    val name: String,
    val colorId: ColorId
)

fun Intent.getNewTeamDto(): NewTeamDto {
    return NewTeamDto(
        getStringExtra("name"),
        getSerializableExtra("colorId") as ColorId
    )
}

fun Intent.putNewTeamDto(newTeamDto: NewTeamDto): Intent {
    putExtra("name", newTeamDto.name)
    putExtra("colorId", newTeamDto.colorId)
    return this
}