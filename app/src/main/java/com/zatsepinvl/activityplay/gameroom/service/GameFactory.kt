package com.zatsepinvl.activityplay.gameroom.service

import com.zatsepinvl.activityplay.core.ActivityGame
import com.zatsepinvl.activityplay.gameroom.model.GameRoomState

interface GameFactory {
    fun createGame(roomState: GameRoomState): ActivityGame
}