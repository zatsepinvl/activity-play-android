package com.zatsepinvl.activityplay.gameroom.service

import com.zatsepinvl.activityplay.core.ActivityGame
import com.zatsepinvl.activityplay.core.model.GameSettings
import com.zatsepinvl.activityplay.gameroom.model.GameRoomMode
import com.zatsepinvl.activityplay.gameroom.model.GameRoomState
import com.zatsepinvl.activityplay.team.model.Team
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@JvmSuppressWildcards
class GameRoomManagerImpl @Inject constructor(
    private val gameFactory: GameFactory,
    private val roomStateRepositories: Map<GameRoomMode, GameRoomStateRepository>
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

    private val currentGameRootRepository: GameRoomStateRepository
        get() {
            val gameMode = currentRoomState.gameMode
            return roomStateRepositories[gameMode]
                ?: throw IllegalStateException(
                    "No implementation of ${GameRoomStateRepository::class} found for game mode $gameMode."
                )
        }

    override fun updateRoomState(roomState: GameRoomState): GameRoomState {
        _currentRoomState = roomState
        currentGameRootRepository.updateGameRoomState(roomState)
        return roomState
    }

    override fun updateGame(game: ActivityGame): ActivityGame {
        val gameState = game.save()
        updateRoomState(currentRoomState.copy(gameState = gameState))
        return currentGame
    }

    override fun updateGameSettings(gameSettings: GameSettings): GameRoomState {
        updateRoomState(currentRoomState.copy(gameSettings = gameSettings))
        return currentRoomState
    }
}