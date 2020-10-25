package com.zatsepinvl.activityplay.game.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.android.viewmodel.SingleLiveEvent
import com.zatsepinvl.activityplay.gameroom.service.GameRoomManager
import com.zatsepinvl.activityplay.timer.Timer
import javax.inject.Inject

class TimerViewModel @Inject constructor(
    private val roomManager: GameRoomManager,
    private val timer: Timer
) : ViewModel() {

    val remainingTimeSeconds = MutableLiveData<Int>()
    val timerFinishedEvent = SingleLiveEvent<Void>()

    private var isTimerStopped = true
    private val timerSettings get() = roomManager.currentRoomState.timerSettings

    init {
        timer
            .onTick { secondsLeft -> remainingTimeSeconds.value = secondsLeft }
            .onFinish { timerFinishedEvent.call() }
    }

    fun startRoundTimer() {
        timerFinishedEvent.reset()
        timer.start(timerSettings.roundTimeSeconds)
        isTimerStopped = false
    }

    fun startLastWordTimer() {
        timerFinishedEvent.reset()
        timer.start(timerSettings.lastWordSeconds)
        isTimerStopped = false
    }

    fun stopTimer() {
        if (isTimerStopped) return
        timer.stop()
        isTimerStopped = true
    }

    override fun onCleared() {
        stopTimer()
    }

}