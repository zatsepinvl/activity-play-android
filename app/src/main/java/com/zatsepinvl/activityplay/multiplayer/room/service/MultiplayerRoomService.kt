package com.zatsepinvl.activityplay.multiplayer.room.service

import com.zatsepinvl.activityplay.multiplayer.room.model.MultiplayerRoomState

interface MultiplayerRoomService {
    suspend fun createRoom(roomState: MultiplayerRoomState)
    suspend fun updateRoom(roomState: MultiplayerRoomState)
    suspend fun getRoomState(roomId: String): MultiplayerRoomState
}

