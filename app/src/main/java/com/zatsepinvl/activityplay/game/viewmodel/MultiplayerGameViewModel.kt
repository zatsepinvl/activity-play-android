package com.zatsepinvl.activityplay.game.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zatsepinvl.activityplay.game.service.GameService
import com.zatsepinvl.activityplay.multiplayer.room.model.Device
import com.zatsepinvl.activityplay.multiplayer.room.model.MultiplayerRoomState
import com.zatsepinvl.activityplay.multiplayer.room.service.MultiplayerRoomService
import com.zatsepinvl.activityplay.team.service.TeamService
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MultiplayerGameViewModel @Inject constructor(
    private val multiplayerRoomService: MultiplayerRoomService,
    private val gameService: GameService,
    private val teamService: TeamService
) : ViewModel() {

    val multiplayerRoomState = MutableLiveData<MultiplayerRoomState>()
    val isMultiplayer = MutableLiveData<Boolean>(false)

    fun hostMultiplayerGame() {
        val hostDevice = Device("hostDeviceId", "hostDeviceName")
        val game = gameService.getSavedGame()
        val roomState = MultiplayerRoomState(
            id = "123456",
            gameSettings = game.settings,
            gameState = game.save(),
            devices = listOf(hostDevice),
            host = hostDevice,
            teams = teamService.getTeams()
        )
        viewModelScope.launch {
            multiplayerRoomService.createRoom(roomState)
            multiplayerRoomState.value = roomState
            isMultiplayer.value = true
        }
    }

    fun joinMultiplayerGame() {
        viewModelScope.launch {
            val roomState = multiplayerRoomService.getRoomState("123456")
            val newState = roomState.copy(
                devices = roomState.devices.toMutableList().apply {
                    add(Device("memberDeviceId", "memberDeviceName"))
                }
            )
            multiplayerRoomService.updateRoom(roomState)
            multiplayerRoomState.value = newState
            isMultiplayer.value = true
        }
    }
}