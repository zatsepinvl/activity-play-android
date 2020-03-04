package com.zatsepinvl.activity.play.effects

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.zatsepinvl.activity.play.R
import java.util.*
import javax.inject.Inject

private val finishTracks = listOf<Int>(
    R.raw.wonround_1_mp3,
    R.raw.wonround_2_mp3,
    R.raw.wonround_3_mp3,
    R.raw.wonround_4_mp3,
    R.raw.wonround_5_mp3,
    R.raw.wonround_6_mp3,
    R.raw.wonround_7_mp3,
    R.raw.wonround_8_mp3,
    R.raw.wonround_9_mp3,
    R.raw.wonround_10_mp3,
    R.raw.wonround_11_mp3
)

class EffectsService @Inject constructor(
    private val context: Context
) {
    private val random = Random()

    fun playRandomFinishTrack() {
        val trackIndex = random.nextInt(finishTracks.size)
        playTrack(finishTracks[trackIndex])
    }

    fun playTimeIsOverTrack() {
        playTrack(R.raw.time_is_over_ogg)
    }

    fun playPlusCoinTrack() {
        playTrack(R.raw.plus_coin_ogg)
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
        val mediaPlayer: MediaPlayer = MediaPlayer.create(context, track) ?: return
        mediaPlayer.start()
    }


}