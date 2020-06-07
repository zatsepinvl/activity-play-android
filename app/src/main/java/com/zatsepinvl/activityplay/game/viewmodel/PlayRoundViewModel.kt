package com.zatsepinvl.activityplay.game.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.core.ActivityGame
import com.zatsepinvl.activityplay.core.model.CompletedTask
import com.zatsepinvl.activityplay.core.model.GameTask
import com.zatsepinvl.activityplay.core.model.TaskResultStatus
import com.zatsepinvl.activityplay.game.service.GameActionService
import com.zatsepinvl.activityplay.game.service.GameService
import com.zatsepinvl.activityplay.team.model.Team
import javax.inject.Inject

const val SECONDS_FOR_LAST_WORD = 10

class PlayRoundViewModel @Inject constructor(
    private val gameService: GameService,
    private val gameActionService: GameActionService
) : ViewModel() {

    val currentTask = MutableLiveData<GameTask>()
    val currentTeamRoundScore = MutableLiveData<Int>()
    val isWordHidden = MutableLiveData<Boolean>()

    lateinit var game: ActivityGame

    lateinit var currentTeam: Team
        private set

    var isPlaying: Boolean = false

    val actionLocalName: String
        get() = gameActionService.getActionLocalName(game.currentGameAction)

    val actionDrawable: Drawable
        get() = gameActionService.getActionDrawable(game.currentGameAction)

    fun toggleWordVisibility() {
        isWordHidden.value = !(isWordHidden.value ?: false)
    }

    fun startRound() {
        game = gameService.getSavedGame()
        currentTeam = gameService.currentTeam()
        isWordHidden.value = false
        currentTask.value = game.startRound()
        updateCurrentTeamRoundScore()
        isPlaying = true
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

    fun finishRound() {
        isPlaying = false
        game.finishRound()
        gameService.saveGame(game)
    }

    fun updateTask(task: CompletedTask, status: TaskResultStatus) {
        game.updateCompletedTaskResult(task, status)
        updateCurrentTeamRoundScore()
    }

    private fun updateCurrentTeamRoundScore() {
        currentTeamRoundScore.value = game.getCurrentTeamRoundResult().score
    }
}