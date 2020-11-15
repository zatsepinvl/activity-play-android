package com.zatsepinvl.activityplay.singleplayer

import com.zatsepinvl.activityplay.gameroom.model.GameRoomState

interface SingleplayerGameService {
    fun startNewSingleplayerGame(): GameRoomState
    fun continueSingleplayerGame(): GameRoomState
}