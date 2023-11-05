package com.zatsepinvl.activityplay.game.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.android.viewmodel.SingleLiveEvent
import com.zatsepinvl.activityplay.settings.service.GameSettingsService
import com.zatsepinvl.activityplay.timer.Timer
import javax.inject.Inject

class TimerViewModel @Inject constructor(
    private val settingsService: GameSettingsService,
    private val timer: Timer
) : ViewModel() {

    val remainingTimeSeconds = MutableLiveData<Int>()
    val timerFinishedEvent = SingleLiveEvent<Timer>()

    init {
        timer
            .onTick { secondsLeft -> remainingTimeSeconds.value = secondsLeft }
            .onFinish { timerFinishedEvent.value = timer }
    }

    fun startRoundTimer() {
        timerFinishedEvent.reset()
        val duration = settingsService.getSecondsForRound()
        timer.start(duration)
    }

    fun startLastWordTimer() {
        timerFinishedEvent.reset()
        timer.start(SECONDS_FOR_LAST_WORD)
    }

    fun stopTimer() {
        timer.stop()
    }

    override fun onCleared() {
        timer.stop()
    }
}