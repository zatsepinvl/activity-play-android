package com.zatsepinvl.activityplay.multiplayer.room.service

import com.zatsepinvl.activityplay.multiplayer.room.model.MultiplayerRoomState
import com.zatsepinvl.activityplay.multiplayer.storage.RealtimeStorage
import javax.inject.Inject

interface MultiplayerRoomFactory {
    fun createRoom(): MultiplayerRoom
    fun joinRoom(id: String): MultiplayerRoom
}

class MultiplayerRoomFactoryImpl @Inject constructor(
    private val realtimeStorage: RealtimeStorage<MultiplayerRoomState>
) : MultiplayerRoomFactory {
    override fun createRoom(): MultiplayerRoom {
        TODO("Not yet implemented")
    }

    override fun joinRoom(id: String): MultiplayerRoom {
        TODO("Not yet implemented")
    }
}