package com.zatsepinvl.activityplay.game.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zatsepinvl.activityplay.game.service.GameService
import com.zatsepinvl.activityplay.log.APP_LOG_TAG
import com.zatsepinvl.activityplay.multiplayer.room.model.Device
import com.zatsepinvl.activityplay.multiplayer.room.model.MultiplayerRoomState
import com.zatsepinvl.activityplay.multiplayer.room.service.MultiplayerRoomService
import com.zatsepinvl.activityplay.team.service.TeamService
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

enum class RoomLoadingState {
    NONE,
    LOADING,
    LOADED,
    ERROR
}

//ToDo: refactor this viewmodel
class MultiplayerGameViewModel @Inject constructor(
    private val multiplayerRoomService: MultiplayerRoomService,
    private val gameService: GameService,
    private val teamService: TeamService
) : ViewModel() {

    val multiplayerRoomState = MutableLiveData<MultiplayerRoomState>()
    val isMultiplayer = MutableLiveData(false)
    val roomLoadingState = MutableLiveData(RoomLoadingState.NONE)
    val roomLoadingErrorMessage = MutableLiveData<String>(null)

    fun hostMultiplayerGame() {
        val hostDevice = Device("hostDeviceId", "hostDeviceName")
        val game = gameService.getSavedGame()
        val roomState = MultiplayerRoomState(
            roomId = "123456",
            gameSettings = game.settings,
            gameState = game.save(),
            devices = listOf(hostDevice),
            host = hostDevice,
            teams = teamService.getTeams(),
            createdAt = Date()
        )
        viewModelScope.launch {
            multiplayerRoomService.createRoom(roomState)
            multiplayerRoomState.value = roomState
            isMultiplayer.value = true
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
                val game = gameService.createNewGame()
                game.load(newState.gameState!!)
                gameService.saveGame(game)
                isMultiplayer.value = true
                roomLoadingState.value = RoomLoadingState.LOADED
            } catch (e: Exception) {
                //ToDo: catch exceptions
                Log.e(APP_LOG_TAG, "Can not load room", e)
                roomLoadingState.value = RoomLoadingState.ERROR
                roomLoadingErrorMessage.value = e.message
            }

        }
    }
}