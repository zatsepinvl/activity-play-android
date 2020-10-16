package com.zatsepinvl.activityplay.multiplayer.room.service

import com.zatsepinvl.activityplay.multiplayer.room.model.MultiplayerRoomState
import com.zatsepinvl.activityplay.multiplayer.room.storage.MultiplayerRoomStateStorage
import javax.inject.Inject

class MultiplayerRoomServiceImpl @Inject constructor(
    private val realtimeStorage: MultiplayerRoomStateStorage
) : MultiplayerRoomService {
    override suspend fun createRoom(roomState: MultiplayerRoomState) {
        realtimeStorage.saveItem(roomState.roomId, roomState)
    }

    override suspend fun updateRoom(roomState: MultiplayerRoomState) {
        realtimeStorage.saveItem(roomState.roomId, roomState)
    }

    override suspend fun getRoomState(roomId: String): MultiplayerRoomState {
        //ToDo: handle the empty response appropriately
        return realtimeStorage.getItem(roomId)!!
    }
}