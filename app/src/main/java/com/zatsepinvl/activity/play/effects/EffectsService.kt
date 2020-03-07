package com.zatsepinvl.activity.play.effects

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.settings.service.ActivityPlayPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EffectsService @Inject constructor(private val context: Context) {

    fun playStartRoundTrack() {
        playTrack(R.raw.grand_opening)
    }

    fun playPlusCoinTrack() {
        playTrack(R.raw.fruit)
    }

    fun playTimeIsOverTrack() {
        playTrack(R.raw.roots)
    }

    fun playFinishTrack() {
        playTrack(R.raw.flower)
    }

    fun playGameOverTrack() {
        playTrack(R.raw.game_over)
    }

    fun vibrate() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

    private fun playTrack(track: Int) {
        if (!ActivityPlayPreference.getSoundsEnabled(context)) {
            return
        }
        val mediaPlayer: MediaPlayer = MediaPlayer.create(context, track) ?: return
        mediaPlayer.start()
    }
}