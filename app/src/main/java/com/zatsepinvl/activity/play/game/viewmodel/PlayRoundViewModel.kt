package com.zatsepinvl.activity.play.game.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.android.viewmodel.SingleLiveEvent
import com.zatsepinvl.activity.play.core.ActivityGame
import com.zatsepinvl.activity.play.core.model.GameTask
import com.zatsepinvl.activity.play.game.service.GameService
import com.zatsepinvl.activity.play.settings.service.GameSettingsService
import com.zatsepinvl.activity.play.team.model.Team
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PlayRoundViewModel @Inject constructor(
    private val gameService: GameService,
    private val settingsService: GameSettingsService
) : ViewModel() {

    val remainingTimeSeconds = MutableLiveData<Int>()
    val currentTask = MutableLiveData<GameTask>()
    val currentTeamRoundScore = MutableLiveData<Int>()
    val isWordHidden = MutableLiveData<Boolean>()
    val finishRoundEvent = SingleLiveEvent<Void>()

    lateinit var currentTeam: Team
        private set
    var roundPlaying = false


    private var timer: CountDownTimer? = null
    private lateinit var game: ActivityGame


    fun toggleWordVisibility() {
        isWordHidden.value = !(isWordHidden.value ?: false)
    }

    fun start() {
        stopTimer()
        isWordHidden.value = false
        game = gameService.getSavedGame()
        currentTeam = gameService.currentTeam()
        currentTask.value = game.startRound()
        updateCurrentTeamRoundScore()
        startTimer()
        roundPlaying = true
    }

    fun completeTask() {
        if (!game.roundIsPlaying) return
        currentTask.value = game.completeCurrentTask()
        isWordHidden.value = false
        updateCurrentTeamRoundScore()
    }

    fun skipTask() {
        if (!game.roundIsPlaying) return
        currentTask.value = game.skipCurrentTask()
        updateCurrentTeamRoundScore()
    }

    fun stopRound() {
        stopTimer()
        finishRoundEvent.call()
        roundPlaying = false
    }

    fun completeLastWord() {
        game.completeCurrentTask()
        updateCurrentTeamRoundScore()
    }

    fun finishRound() {
        game.finishRound()
        gameService.saveGame(game)
    }

    private fun startTimer() {
        val secondsPerRound = settingsService.getSecondsForRound()
        remainingTimeSeconds.value = secondsPerRound
        timer = object : CountDownTimer(
            TimeUnit.SECONDS.toMillis(secondsPerRound.toLong()),
            TimeUnit.SECONDS.toMillis(1)
        ) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeSeconds.value = TimeUnit.MILLISECONDS
                    .toSeconds(millisUntilFinished)
                    .toInt()
            }

            override fun onFinish() {
                stopRound()
            }
        }.start()
    }

    private fun stopTimer() {
        timer?.cancel()
    }

    private fun updateCurrentTeamRoundScore() {
        currentTeamRoundScore.value = game.getTeamRoundScore(
            game.currentTeamIndex,
            game.currentRoundIndex
        )
    }
}