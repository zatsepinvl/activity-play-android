package com.zatsepinvl.activityplay.multiplayer.room.service

import com.zatsepinvl.activityplay.gameroom.model.GameRoomState
import com.zatsepinvl.activityplay.multiplayer.room.storage.MultiplayerRoomStateStorage
import com.zatsepinvl.activityplay.multiplayer.storage.ItemChangedListener
import javax.inject.Inject

class MultiplayerRoomServiceImpl @Inject constructor(
    private val realtimeStorage: MultiplayerRoomStateStorage
) : MultiplayerRoomService {
    override suspend fun createRoom(roomState: GameRoomState) {
        realtimeStorage.saveItem(roomState.roomId, roomState)
    }

    override suspend fun updateRoom(roomState: GameRoomState) {
        realtimeStorage.saveItem(roomState.roomId, roomState)
    }

    override suspend fun getRoomState(roomId: String): GameRoomState {
        //ToDo: handle the empty response appropriately
        return realtimeStorage.getItem(roomId)!!
    }

    override fun subscribeOnRoomStateChanges(
        roomId: String,
        listener: ItemChangedListener<GameRoomState>
    ) {
        realtimeStorage.addOnItemChangedListener(
            roomId,
            listener
        )
    }
}