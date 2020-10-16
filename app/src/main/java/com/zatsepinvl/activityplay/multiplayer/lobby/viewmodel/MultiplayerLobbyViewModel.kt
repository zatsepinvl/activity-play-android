package com.zatsepinvl.activityplay.multiplayer.lobby.viewmodel

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.android.viewmodel.SingleLiveEvent
import javax.inject.Inject

data class JoinNewGameEventData(
    val roomId: String
)

class MultiplayerLobbyViewModel @Inject constructor() : ViewModel() {

    val createNewGameEvent = SingleLiveEvent<Void>()
    val joinGameEvent = SingleLiveEvent<JoinNewGameEventData>()

    fun createNewGame() {
        createNewGameEvent.call()
    }

    fun joinGame(roomId: String) {
        joinGameEvent.call(JoinNewGameEventData(roomId))
    }
}