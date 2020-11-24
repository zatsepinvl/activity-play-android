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
import com.zatsepinvl.activityplay.gameroom.model.GameRoomState
import com.zatsepinvl.activityplay.gameroom.service.GameRoomManager
import com.zatsepinvl.activityplay.settings.service.GameSettingsService
import com.zatsepinvl.activityplay.team.model.Team
import com.zatsepinvl.activityplay.timer.Timer
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val gameActionService: GameActionService,
    private val roomManager: GameRoomManager,
    private val timer: Timer,
) : ViewModel() {

    //LiveData
    val currentTeam = MutableLiveData<Team>()
    val currentTeamRoundScore = MutableLiveData<Int>()
    val currentTask = MutableLiveData<GameTask>()
    val remainingTimeSeconds = MutableLiveData<Int>()

    //Events
    val roundStartedEvent = SingleLiveEvent<Void>()
    val taskCompletedEvent = SingleLiveEvent<Void>()
    val taskFailedEvent = SingleLiveEvent<Void>()
    val lastTaskFinishedEvent = SingleLiveEvent<Void>()
    val mainPartFinishedEvent = SingleLiveEvent<Void>()
    val roundFinishedEvent = SingleLiveEvent<Void>()

    var isPlaying: Boolean = false

    val actionLocalName: String
        get() = gameActionService.getActionLocalName(game.currentGameAction)

    val actionDrawable: Drawable
        get() = gameActionService.getActionDrawable(game.currentGameAction)

    lateinit var game: ActivityGame
    lateinit var gameRoom: GameRoomState

    private var isTimerStopped = true

    fun startRound() {
        game = roomManager.currentGame
        gameRoom = roomManager.currentRoomState
        currentTeam.value = roomManager.currentTeam
        currentTask.value = game.startRound()
        updateCurrentTeamRoundScore()
        isPlaying = true
        startRoundTimer()
        roundStartedEvent.call()
    }

    fun completeTask() {
        doCompleteTask()
        taskCompletedEvent.call()
    }

    private fun doCompleteTask() {
        if (!game.roundIsPlaying) return
        currentTask.value = game.completeCurrentTask()
        updateCurrentTeamRoundScore()
    }

    fun failTask() {
        doFailTask()
        taskFailedEvent.call()
    }

    private fun doFailTask() {
        if (!game.roundIsPlaying) return
        currentTask.value = game.failCurrentTask()
        updateCurrentTeamRoundScore()
    }

    fun completeLastTask() {
        doCompleteTask()
        onLastTaskFinished()
    }

    fun failLastTask() {
        doFailTask()
        onLastTaskFinished()
    }

    fun skipLastTask() {
        game.skipCurrentTask()
        onLastTaskFinished()
    }

    private fun onLastTaskFinished() {
        timer.stop()
        lastTaskFinishedEvent.call()
    }

    fun finishMainPart() {
        timer.stop()
        startLastWordTimer()
        mainPartFinishedEvent.call()
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

    private fun startRoundTimer() {
        startTimer { finishMainPart() }
        timer.start(gameRoom.timerSettings.roundTimeSeconds)
        isTimerStopped = false
    }

    private fun startLastWordTimer() {
        startTimer { skipLastTask() }
        timer.start(gameRoom.timerSettings.lastWordSeconds)
        isTimerStopped = false
    }

    private fun startTimer(onFinish: () -> Unit) {
        timer
            .onTick { secondsLeft -> remainingTimeSeconds.value = secondsLeft }
            .onFinish(onFinish)
    }

    private fun stopTimerIfRunning() {
        if (isTimerStopped) return
        timer.stop()
        isTimerStopped = true
    }

    override fun onCleared() {
        stopTimerIfRunning()
    }

    private fun updateCurrentTeamRoundScore() {
        currentTeamRoundScore.value = game.getCurrentTeamRoundResult().score
    }
}