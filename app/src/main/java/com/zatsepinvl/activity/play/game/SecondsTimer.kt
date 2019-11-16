package com.zatsepinvl.activity.play.game

import java.util.*
import java.util.concurrent.TimeUnit

interface TimerListener {
    fun onTick()
    fun onFinish()
}

interface SecondsTimer {
    fun start(listener: TimerListener, durationSecs: Int)
}

class SecondsTimerImpl : SecondsTimer {
    private val timer = Timer()
    override fun start(listener: TimerListener, durationSecs: Int) {
        var secondsLeft = durationSecs
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    listener.onTick()
                    secondsLeft--
                    if (secondsLeft == 0) {
                        timer.cancel()
                        listener.onFinish()
                    }
                }
            }, 0, TimeUnit.SECONDS.toMillis(1)
        )
    }

}