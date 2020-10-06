package com.zatsepinvl.activityplay.multiplayer.room.service

import com.zatsepinvl.activityplay.multiplayer.room.model.MultiplayerRoomState
import com.zatsepinvl.activityplay.multiplayer.storage.RealtimeStorage

interface MultiplayerRoom {
    fun getState(): MultiplayerRoomState
}

class SimpleMultiplayerRoom(
    private val realtimeStorage: RealtimeStorage<MultiplayerRoomState>
) : MultiplayerRoom {

    override fun getState(): MultiplayerRoomState {
        TODO("Not yet implemented")
    }

}
