package com.zatsepinvl.activityplay.multiplayer.lobby.viewmodel

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.android.viewmodel.SingleLiveEvent
import javax.inject.Inject

data class JoinNewGameEventData(
    val roomId: String
)

class MultiplayerLobbyViewModel @Inject constructor() : ViewModel() {

    val navigateToCreateNewGame = SingleLiveEvent<Void>()
    val navigateToJoinNewGame = SingleLiveEvent<JoinNewGameEventData>()

    fun createNewGame() {
        navigateToCreateNewGame.call()
    }

    fun joinGame(roomId: String) {
        navigateToJoinNewGame.call(JoinNewGameEventData(roomId))
    }
}