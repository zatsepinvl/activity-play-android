package com.zatsepinvl.activityplay.gameroom.model

import com.zatsepinvl.activityplay.core.model.GameSettings
import com.zatsepinvl.activityplay.core.model.GameState
import com.zatsepinvl.activityplay.settings.model.TimerSettings
import com.zatsepinvl.activityplay.team.model.Team
import java.util.*

data class GameRoomState(
    val roomId: String,
    val gameMode: GameRoomMode,
    val host: Device,
    val gameState: GameState,
    val gameSettings: GameSettings,
    val timerSettings: TimerSettings,
    val devices: List<Device>,
    val teams: List<Team>,
    val createdAt: Date = Date(),
    val dictionaryLanguage: String
) {
    val currentTeam: Team
        get() = teams[gameState.currentTeamIndex]
}