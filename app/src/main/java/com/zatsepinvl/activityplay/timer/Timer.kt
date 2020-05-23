package com.zatsepinvl.activityplay.timer

import android.os.CountDownTimer
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject

typealias onTickF = (secondsLeft: Int) -> Unit
typealias onFinishF = () -> Unit

class Timer @Inject constructor() {
    private var timer: CountDownTimer? = null

    private var onTickFunc: (onTickF)? = null
    private var onFinishFunc: (onFinishF)? = null

    fun onTick(func: onTickF): Timer = apply { onTickFunc = func }
    fun onFinish(func: onFinishF): Timer = apply { onFinishFunc = func }

    fun start(durationSeconds: Int) {
        check(timer == null) {
            "Timer can not be restarted when it's working. You should stop it before start again."
        }
        timer = object : CountDownTimer(
            SECONDS.toMillis(durationSeconds.toLong()),
            SECONDS.toMillis(1)
        ) {
            override fun onTick(millisUntilFinished: Long) {
                onTickFunc?.invoke(
                    TimeUnit.MILLISECONDS
                        .toSeconds(millisUntilFinished)
                        .toInt()
                )
            }

            override fun onFinish() {
                timer = null
                onFinishFunc?.invoke()
            }
        }.start()
    }

    fun stop() {
        timer?.cancel()
        timer = null
    }
}