package com.zatsepinvl.activity.play.game

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class SecondsTimerImplTest {

    @Test(timeout = 1000)
    fun start_for_1_sec() {
        var ticks = 0
        val finishIsInvoked = AtomicBoolean(false)
        val timer = SecondsTimerImpl()
        timer.start(object : TimerListener {
            override fun onTick() {
                ticks++
            }

            override fun onFinish() {
                finishIsInvoked.set(true)
            }
        }, 1)

        while (!finishIsInvoked.get()) {
        }

        assertEquals(1, ticks)
    }
}