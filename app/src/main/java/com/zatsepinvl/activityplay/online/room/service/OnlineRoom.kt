package com.zatsepinvl.activityplay.online.room.service

import com.zatsepinvl.activityplay.online.room.model.OnlineRoomState
import com.zatsepinvl.activityplay.online.storage.RealtimeStorage

interface OnlineRoom {
    fun getState(): OnlineRoomState
}

class SimpleOnlineRoom(
    private val realtimeStorage: RealtimeStorage<OnlineRoomState>
) : OnlineRoom {

    override fun getState(): OnlineRoomState {
        TODO("Not yet implemented")
    }

}
