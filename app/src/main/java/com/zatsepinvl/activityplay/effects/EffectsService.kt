package com.zatsepinvl.activityplay.effects

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.settings.service.ActivityPlayPreference
import javax.inject.Inject
import javax.inject.Singleton

private const val VIBRATION_DURATION = 100L

@Singleton
class EffectsService @Inject constructor(private val context: Context) {

    fun playStartRoundTrack() {
        playTrack(R.raw.grand_opening)
    }

    fun playPlusCoinTrack() {
        playTrack(R.raw.fruit)
    }

    fun playTimeIsOverTrack() {
        playTrack(R.raw.clock_alarm, 2)
    }

    fun playFinishTrack() {
        playTrack(R.raw.flower)
    }

    fun playGameOverTrack() {
        playTrack(R.raw.game_over)
    }

    fun vibrate() {
        if (!ActivityPlayPreference.getVibrationEnabled(context)) {
            return
        }
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            val effect = VibrationEffect.createOneShot(
                VIBRATION_DURATION,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
            vibrator.vibrate(effect)
        } else {
            vibrator.vibrate(VIBRATION_DURATION)
        }
    }

    private fun playTrack(track: Int, durationSec: Int? = null) {
        if (!ActivityPlayPreference.getSoundsEnabled(context)) {
            return
        }
        val mediaPlayer: MediaPlayer = MediaPlayer.create(context, track) ?: return
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
        if (durationSec != null) {
            object : CountDownTimer(durationSec * 1000L, 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    mediaPlayer.stop()
                }
            }.start()
        }
    }
}