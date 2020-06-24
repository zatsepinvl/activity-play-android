package com.zatsepinvl.activityplay.multiplayer.room.model

import com.zatsepinvl.activityplay.core.model.GameSettings
import com.zatsepinvl.activityplay.core.model.GameState
import com.zatsepinvl.activityplay.team.model.Team

data class MutiplayerRoomState(
    val id: String,
    val host: Device,
    val gameState: GameState? = null,
    val gameSettings: GameSettings,
    val devices: List<Device>,
    val teams: List<Team>
)