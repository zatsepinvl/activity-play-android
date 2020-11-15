package com.zatsepinvl.activityplay.gameroom.service

import com.zatsepinvl.activityplay.gameroom.model.GameRoomState

interface GameRoomStateRepository {
    fun updateGameRoomState(roomState: GameRoomState)
}