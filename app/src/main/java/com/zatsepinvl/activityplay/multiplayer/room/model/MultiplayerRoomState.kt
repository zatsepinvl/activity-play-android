package com.zatsepinvl.activityplay.multiplayer.room.model

import com.zatsepinvl.activityplay.core.model.GameSettings
import com.zatsepinvl.activityplay.core.model.GameState
import com.zatsepinvl.activityplay.gameroom.model.Device
import com.zatsepinvl.activityplay.team.model.Team
import java.util.*

//Firestore requires a no-argument constructor
data class MultiplayerRoomState(
    val roomId: String = "",
    val host: Device? = null,
    val gameState: GameState? = null,
    val gameSettings: GameSettings? = null,
    val devices: List<Device> = listOf(),
    val teams: List<Team> = listOf(),
    val createdAt: Date = Date()
)