package com.zatsepinvl.activityplay.multiplayer.room.service

import com.zatsepinvl.activityplay.gameroom.model.GameRoomState
import com.zatsepinvl.activityplay.multiplayer.storage.ItemChangedListener

interface MultiplayerRoomService {
    suspend fun createRoom(roomState: GameRoomState)
    suspend fun updateRoom(roomState: GameRoomState)
    suspend fun getRoomState(roomId: String): GameRoomState
    fun subscribeOnRoomStateChanges(
        roomId: String,
        listener: ItemChangedListener<GameRoomState>
    )
}

