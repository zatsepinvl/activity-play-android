package com.zatsepinvl.activityplay.multiplayer.room.service

import com.zatsepinvl.activityplay.multiplayer.room.model.MutiplayerRoomState
import com.zatsepinvl.activityplay.multiplayer.storage.RealtimeStorage

interface MultiplayerRoom {
    fun getState(): MutiplayerRoomState
}

class SimpleMultiplayerRoom(
    private val realtimeStorage: RealtimeStorage<MutiplayerRoomState>
) : MultiplayerRoom {

    override fun getState(): MutiplayerRoomState {
        TODO("Not yet implemented")
    }

}
