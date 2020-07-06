package com.zatsepinvl.activityplay.multiplayer.lobby.viewmodel

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.android.viewmodel.SingleLiveEvent
import javax.inject.Inject

data class JoinNewGameEventData(
    val roomId: String
)

class MultiplayerLobbyViewModel @Inject constructor() : ViewModel() {

    val setupNewGameEvent = SingleLiveEvent<Void>()
    val joinNewGameEvent = SingleLiveEvent<JoinNewGameEventData>()

    fun createNewGame() {
        setupNewGameEvent.call()
    }

    fun joinGame(roomId: String) {
        joinNewGameEvent.call(JoinNewGameEventData(roomId))
    }
}