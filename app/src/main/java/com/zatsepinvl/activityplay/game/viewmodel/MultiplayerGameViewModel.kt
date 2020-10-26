package com.zatsepinvl.activityplay.game.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zatsepinvl.activityplay.android.viewmodel.SingleLiveEvent
import com.zatsepinvl.activityplay.core.model.GameState
import com.zatsepinvl.activityplay.gameroom.model.Device
import com.zatsepinvl.activityplay.gameroom.model.GameRoomState
import com.zatsepinvl.activityplay.gameroom.service.GameRoomManager
import com.zatsepinvl.activityplay.log.APP_LOG_TAG
import com.zatsepinvl.activityplay.multiplayer.room.service.MultiplayerRoomService
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

enum class RoomLoadingState {
    NONE,
    LOADING,
    LOADED,
    ERROR
}

//ToDo: refactor this viewmodel
@Singleton
class MultiplayerGameViewModel @Inject constructor(
    private val multiplayerRoomService: MultiplayerRoomService,
    private val gameRoomManager: GameRoomManager,
) : ViewModel() {

    val multiplayerRoomState = MutableLiveData<GameRoomState>()
    val multiplayerRoomStateUpdated = SingleLiveEvent<Void>()
    val isMultiplayer = MutableLiveData(false)
    val roomLoadingState = MutableLiveData(RoomLoadingState.NONE)
    val roomLoadingErrorMessage = MutableLiveData<String>(null)

    fun updateRoomState(gameState: GameState) {
        viewModelScope.launch {
            val roomState = multiplayerRoomState.value!!
                .copy(
                    gameState = gameState
                )
            multiplayerRoomService.updateRoom(roomState)
        }
    }

    fun hostMultiplayerGame() {
        val roomState = gameRoomManager.startSingleplayerGame()
        viewModelScope.launch {
            //multiplayerRoomService.createRoom(roomState)
            //multiplayerRoomState.value = roomState
            isMultiplayer.value = true
            subscribeOnRoomChanges()
        }
    }

    fun joinMultiplayerGame(roomId: String) {
        roomLoadingState.value = RoomLoadingState.LOADING
        viewModelScope.launch {
            try {
                val roomState = multiplayerRoomService.getRoomState("123456")
                val newState = roomState.copy(
                    devices = roomState.devices.toMutableList().apply {
                        add(Device("memberDeviceId", "memberDeviceName"))
                    }
                )
                multiplayerRoomService.updateRoom(newState)
                multiplayerRoomState.value = newState
                val gameState = gameRoomManager.startSingleplayerGame()
                isMultiplayer.value = true
                subscribeOnRoomChanges()
                roomLoadingState.value = RoomLoadingState.LOADED
            } catch (e: Exception) {
                //ToDo: catch exceptions
                Log.e(APP_LOG_TAG, "Can not load room", e)
                roomLoadingState.value = RoomLoadingState.ERROR
                roomLoadingErrorMessage.value = e.message
            }

        }
    }

    private fun subscribeOnRoomChanges() {
        multiplayerRoomService.subscribeOnRoomStateChanges(multiplayerRoomState.value!!.roomId) { item ->
            multiplayerRoomState.value = item
            val gameState = gameRoomManager.startSingleplayerGame()
            multiplayerRoomStateUpdated.call()
        }
    }
}