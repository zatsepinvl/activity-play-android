package com.zatsepinvl.activityplay.gameroom.service

import com.zatsepinvl.activityplay.core.ActivityGame
import com.zatsepinvl.activityplay.core.model.GameStateFactory
import com.zatsepinvl.activityplay.device.DeviceService
import com.zatsepinvl.activityplay.dictionary.DictionaryHolder
import com.zatsepinvl.activityplay.gameroom.model.GameRoomMode
import com.zatsepinvl.activityplay.gameroom.model.GameRoomState
import com.zatsepinvl.activityplay.gamestate.service.GameStateService
import com.zatsepinvl.activityplay.settings.service.GameSettingsService
import com.zatsepinvl.activityplay.team.model.Team
import com.zatsepinvl.activityplay.team.service.TeamService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRoomManagerImpl @Inject constructor(
    private val gameStateService: GameStateService,
    private val gameSettingsService: GameSettingsService,
    private val teamService: TeamService,
    private val deviceService: DeviceService,
    private val dictionaryHolder: DictionaryHolder,
    private val gameFactory: GameFactory,
) : GameRoomManager {

    private lateinit var _currentRoomState: GameRoomState

    override val currentRoomState: GameRoomState
        get() {
            require(this::_currentRoomState.isInitialized) {
                "Current game is empty. Create or continue game first."
            }
            return _currentRoomState
        }

    override val teams: List<Team>
        get() = currentRoomState.teams

    override val currentTeam: Team
        get() = currentRoomState.currentTeam

    override val currentGame: ActivityGame
        get() = gameFactory.createGame(currentRoomState)

    override fun startSingleplayerGame(): ActivityGame {
        val host = deviceService.getCurrentDevice()
        _currentRoomState = GameRoomState(
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
        return currentGame
    }

    override fun continueSingleplayerGame(): ActivityGame {
        val host = deviceService.getCurrentDevice()
        _currentRoomState = GameRoomState(
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
        return currentGame
    }

    override fun updateRoomState(roomState: GameRoomState): GameRoomState {
        if (roomState.gameMode == GameRoomMode.SINGLEPLAYER) {
            gameStateService.saveGame(roomState.gameState)
        }
        _currentRoomState = roomState
        return roomState
    }

    override fun updateGame(game: ActivityGame): ActivityGame {
        val gameState = game.save()
        updateRoomState(currentRoomState.copy(gameState = gameState))
        return currentGame
    }
}