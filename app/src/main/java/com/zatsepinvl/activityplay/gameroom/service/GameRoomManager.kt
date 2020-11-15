package com.zatsepinvl.activityplay.gameroom.service

import com.zatsepinvl.activityplay.core.ActivityGame
import com.zatsepinvl.activityplay.core.model.GameSettings
import com.zatsepinvl.activityplay.gameroom.model.GameRoomState
import com.zatsepinvl.activityplay.team.model.Team

interface GameRoomManager {
    val teams: List<Team>
    val currentTeam: Team
    val currentGame: ActivityGame
    val currentRoomState: GameRoomState

    fun updateRoomState(roomState: GameRoomState): GameRoomState
    fun updateGame(game: ActivityGame): ActivityGame
    fun updateGameSettings(gameSettings: GameSettings): GameRoomState
}