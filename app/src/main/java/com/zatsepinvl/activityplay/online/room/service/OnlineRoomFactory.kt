package com.zatsepinvl.activityplay.online.room.service

import com.zatsepinvl.activityplay.online.room.model.OnlineRoomState
import com.zatsepinvl.activityplay.online.storage.RealtimeStorage
import javax.inject.Inject

interface OnlineRoomFactory {
    fun createRoom(): OnlineRoom
    fun joinRoom(id: String): OnlineRoom
}

class OnlineRoomFactoryImpl @Inject constructor(
    private val realtimeStorage: RealtimeStorage<OnlineRoomState>
) : OnlineRoomFactory {
    override fun createRoom(): OnlineRoom {
        TODO("Not yet implemented")
    }

    override fun joinRoom(id: String): OnlineRoom {
        TODO("Not yet implemented")
    }
}