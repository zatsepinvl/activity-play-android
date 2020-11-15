package com.zatsepinvl.activityplay.singleplayer

import com.zatsepinvl.activityplay.core.model.GameStateFactory
import com.zatsepinvl.activityplay.device.DeviceService
import com.zatsepinvl.activityplay.dictionary.DictionaryHolder
import com.zatsepinvl.activityplay.gameroom.model.GameRoomMode
import com.zatsepinvl.activityplay.gameroom.model.GameRoomState
import com.zatsepinvl.activityplay.gameroom.service.GameRoomStateRepository
import com.zatsepinvl.activityplay.gamestate.service.GameStateService
import com.zatsepinvl.activityplay.settings.service.GameSettingsService
import com.zatsepinvl.activityplay.team.service.TeamService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SingleplayerGameServiceImpl @Inject constructor(
    private val gameStateService: GameStateService,
    private val gameSettingsService: GameSettingsService,
    private val teamService: TeamService,
    private val deviceService: DeviceService,
    private val dictionaryHolder: DictionaryHolder,
) : SingleplayerGameService, GameRoomStateRepository {

    override fun startNewSingleplayerGame(): GameRoomState {
        val host = deviceService.getCurrentDevice()
        return GameRoomState(
            roomId = "singleplayer",
            gameMode = GameRoomMode.SINGLEPLAYER,
            host = host,
            gameState = GameStateFactory.defaultGameState(),
            gameSettings = gameSettingsService.getGameSettings(),
            timerSettings = gameSettingsService.getTimerSettings(),
            devices = listOf(host),
            teams = teamService.getTeams(),
            dictionaryLanguage = dictionaryHolder.getDictionary().language
        )
    }

    override fun continueSingleplayerGame(): GameRoomState {
        val host = deviceService.getCurrentDevice()
        return GameRoomState(
            roomId = "singleplayer",
            gameMode = GameRoomMode.SINGLEPLAYER,
            host = host,
            gameState = gameStateService.getSavedGame(),
            gameSettings = gameSettingsService.getGameSettings(),
            timerSettings = gameSettingsService.getTimerSettings(),
            devices = listOf(host),
            teams = teamService.getTeams(),
            dictionaryLanguage = dictionaryHolder.getDictionary().language
        )
    }

    override fun updateGameRoomState(roomState: GameRoomState) {
        gameStateService.saveGame(roomState.gameState)
    }
}