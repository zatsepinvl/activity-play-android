package com.zatsepinvl.activityplay.gamesetup.viewmodel

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.android.viewmodel.SingleLiveEvent
import com.zatsepinvl.activityplay.gameroom.model.GameRoomMode
import com.zatsepinvl.activityplay.gameroom.model.GameRoomMode.SINGLEPLAYER
import com.zatsepinvl.activityplay.gameroom.service.GameRoomManager
import com.zatsepinvl.activityplay.gamesetup.model.GameSetupMode
import com.zatsepinvl.activityplay.gamesetup.model.GameSetupMode.CONTINUE
import com.zatsepinvl.activityplay.gamesetup.model.GameSetupMode.START_NEW
import com.zatsepinvl.activityplay.singleplayer.SingleplayerGameService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameSetupViewModel @Inject constructor(
    private val singleplayerGameService: SingleplayerGameService,
    private val gameRoomManager: GameRoomManager
) : ViewModel() {

    val gameSetupFinishedEvent = SingleLiveEvent<Void>()

    lateinit var targetGameMode: GameRoomMode
        private set

    lateinit var targetGameSetupMode: GameSetupMode
        private set

    fun startGameSetup(gameSetupMode: GameSetupMode, gameMode: GameRoomMode) {
        this.targetGameSetupMode = gameSetupMode
        this.targetGameMode = gameMode
    }

    fun finishGameSetup() {
        require(this::targetGameMode.isInitialized) {
            "Target game room mode should be set before starting a game."
        }
        require(this::targetGameSetupMode.isInitialized) {
            "Target game setup mode should be set before starting a game."
        }
        when (targetGameMode) {
            SINGLEPLAYER -> when (targetGameSetupMode) {
                CONTINUE -> continueSingleplayerGame()
                START_NEW -> startNewSingleplayerGame()
            }
            else -> throw NotImplementedError("Multiplayer is not supported yet")
        }
        gameSetupFinishedEvent.call()
    }

    private fun startNewSingleplayerGame() {
        val roomState = singleplayerGameService.startNewSingleplayerGame()
        gameRoomManager.updateRoomState(roomState)
    }

    private fun continueSingleplayerGame() {
        val roomState = singleplayerGameService.continueSingleplayerGame()
        gameRoomManager.updateRoomState(roomState)
    }
}