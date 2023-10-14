package com.zatsepinvl.activityplay.team.model

import android.content.Intent
import android.os.Bundle
import com.zatsepinvl.activityplay.color.ColorId

data class UpdateTeamDto(
    val id: String,
    val name: String,
    val colorId: ColorId
)

fun Bundle.getUpdateTeamDto(): UpdateTeamDto {
    return UpdateTeamDto(
        getString("id")!!,
        getString("name")!!,
        getSerializable("colorId") as ColorId
    )
}

fun Bundle.putUpdateTeamDto(updateTeamDto: UpdateTeamDto): Bundle {
    putString("id", updateTeamDto.id)
    putString("name", updateTeamDto.name)
    putSerializable("colorId", updateTeamDto.colorId)
    return this
}

fun Intent.getUpdateTeamDto(): UpdateTeamDto {
    return UpdateTeamDto(
        getStringExtra("id")!!,
        getStringExtra("name")!!,
        getSerializableExtra("colorId") as ColorId
    )
}

fun Intent.putUpdateTeamDto(updateTeamDto: UpdateTeamDto): Intent {
    putExtra("id", updateTeamDto.id)
    putExtra("name", updateTeamDto.name)
    putExtra("colorId", updateTeamDto.colorId)
    return this
}