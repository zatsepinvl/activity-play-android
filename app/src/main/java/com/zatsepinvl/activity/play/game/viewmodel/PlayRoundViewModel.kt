package com.zatsepinvl.activity.play.game.viewmodel

import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.android.viewmodel.SingleLiveEvent
import com.zatsepinvl.activity.play.core.ActivityGame
import com.zatsepinvl.activity.play.core.model.GameTask
import com.zatsepinvl.activity.play.effects.EffectsService
import com.zatsepinvl.activity.play.game.service.GameActionService
import com.zatsepinvl.activity.play.game.service.GameService
import com.zatsepinvl.activity.play.settings.service.GameSettingsService
import com.zatsepinvl.activity.play.team.model.Team
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val SECONDS_FOR_LAST_WORD = 10

class PlayRoundViewModel @Inject constructor(
    private val gameService: GameService,
    private val settingsService: GameSettingsService,
    private val gameActionService: GameActionService,
    private val effectsService: EffectsService
) : ViewModel() {

    val remainingTimeSeconds = MutableLiveData<Int>()
    val currentTask = MutableLiveData<GameTask>()
    val currentTeamRoundScore = MutableLiveData<Int>()
    val isWordHidden = MutableLiveData<Boolean>()
    val finishRoundEvent = SingleLiveEvent<Void>()
    val lastWordTimerFinishedEvent = SingleLiveEvent<Void>()

    lateinit var currentTeam: Team
        private set
    var roundPlaying = false


    private var timer: CountDownTimer? = null
    private lateinit var game: ActivityGame

    val actionLocalName: String
        get() = gameActionService.getActionLocalName(game.currentGameAction)

    val actionDrawable: Drawable
        get() = gameActionService.getActionDrawable(game.currentGameAction)

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
        startGameTimer()
        roundPlaying = true
    }

    fun completeTask() {
        if (!game.roundIsPlaying) return
        currentTask.value = game.completeCurrentTask()
        isWordHidden.value = false
        updateCurrentTeamRoundScore()
        effectsService.playPlusCoinTrack()
        effectsService.vibrate()
    }

    fun skipTask() {
        if (!game.roundIsPlaying) return
        currentTask.value = game.skipCurrentTask()
        updateCurrentTeamRoundScore()
        effectsService.vibrate()
    }

    fun stopRound() {
        stopTimer()
        finishRoundEvent.call()
        roundPlaying = false
        effectsService.playTimeIsOverTrack()
        effectsService.vibrate()
    }

    fun completeLastWord() {
        game.completeCurrentTask()
        updateCurrentTeamRoundScore()
        effectsService.playPlusCoinTrack()
    }

    fun finishRound() {
        game.finishRound()
        gameService.saveGame(game)
        stopTimer()
    }

    private fun startGameTimer() {
        val secondsPerRound = settingsService.getSecondsForRound()
        startTimer(secondsPerRound) { stopRound() }
    }

    fun startLastWordTimer() {
        val secondsForLastWord = SECONDS_FOR_LAST_WORD
        startTimer(secondsForLastWord) {
            lastWordTimerFinishedEvent.call()
        }
    }

    override fun onCleared() {
        stopTimer()
    }

    private fun startTimer(durationSeconds: Int, onFinish: () -> Unit) {
        remainingTimeSeconds.value = durationSeconds
        timer = object : CountDownTimer(
            TimeUnit.SECONDS.toMillis(durationSeconds.toLong()),
            TimeUnit.SECONDS.toMillis(1)
        ) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeSeconds.value = TimeUnit.MILLISECONDS
                    .toSeconds(millisUntilFinished)
                    .toInt()
            }

            override fun onFinish() {
                onFinish()
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