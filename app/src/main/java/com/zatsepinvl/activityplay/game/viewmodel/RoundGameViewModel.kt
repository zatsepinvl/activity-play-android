package com.zatsepinvl.activityplay.game.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.android.viewmodel.SingleLiveEvent
import com.zatsepinvl.activityplay.core.ActivityGame
import com.zatsepinvl.activityplay.core.model.CompletedTask
import com.zatsepinvl.activityplay.core.model.GameTask
import com.zatsepinvl.activityplay.core.model.TaskResultStatus
import com.zatsepinvl.activityplay.gameaction.GameActionService
import com.zatsepinvl.activityplay.gameroom.service.GameRoomManager
import com.zatsepinvl.activityplay.team.model.Team
import javax.inject.Inject

class RoundGameViewModel @Inject constructor(
    private val gameActionService: GameActionService,
    private val roomManager: GameRoomManager,
) : ViewModel() {

    //LiveData
    val currentTeam = MutableLiveData<Team>()
    val currentTeamRoundScore = MutableLiveData<Int>()
    val currentTask = MutableLiveData<GameTask>()

    //Events
    val roundFinishedEvent = SingleLiveEvent<Void>()
    val lastTaskFinishedEvent = SingleLiveEvent<Void>()

    var isPlaying: Boolean = false

    val actionLocalName: String
        get() = gameActionService.getActionLocalName(game.currentGameAction)

    val actionDrawable: Drawable
        get() = gameActionService.getActionDrawable(game.currentGameAction)

    lateinit var game: ActivityGame

    fun startRound() {
        game = roomManager.currentGame
        currentTeam.value = roomManager.currentTeam
        currentTask.value = game.startRound()
        updateCurrentTeamRoundScore()
        isPlaying = true
    }

    fun completeTask() {
        if (!game.roundIsPlaying) return
        currentTask.value = game.completeCurrentTask()
        updateCurrentTeamRoundScore()
    }

    fun failTask() {
        if (!game.roundIsPlaying) return
        currentTask.value = game.failCurrentTask()
        updateCurrentTeamRoundScore()
    }

    fun completeLastTask() {
        completeTask()
        lastTaskFinishedEvent.call()
    }

    fun failLastTask() {
        failTask()
        lastTaskFinishedEvent.call()
    }

    fun skipLastTask() {
        game.skipCurrentTask()
        lastTaskFinishedEvent.call()
    }

    fun finishRound() {
        isPlaying = false
        game.finishRound()
        roomManager.updateGame(game)
        roundFinishedEvent.call()
    }

    fun updateTask(task: CompletedTask, status: TaskResultStatus) {
        game.updateCompletedTaskResult(task, status)
        updateCurrentTeamRoundScore()
    }

    private fun updateCurrentTeamRoundScore() {
        currentTeamRoundScore.value = game.getCurrentTeamRoundResult().score
    }
}