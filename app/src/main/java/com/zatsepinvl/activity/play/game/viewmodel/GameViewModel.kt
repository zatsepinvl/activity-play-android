package com.zatsepinvl.activity.play.game.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.core.ActivityGame
import com.zatsepinvl.activity.play.core.model.GameSettings
import com.zatsepinvl.activity.play.core.model.GameTask
import com.zatsepinvl.activity.play.core.totalScoreForLastRound
import com.zatsepinvl.activity.play.game.model.GameStatus
import com.zatsepinvl.activity.play.game.model.TeamBoardItemData
import com.zatsepinvl.activity.play.game.service.GameService
import com.zatsepinvl.activity.play.settings.GameSettingsService
import com.zatsepinvl.activity.play.team.model.Team
import com.zatsepinvl.activity.play.team.service.TeamService
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val gameService: GameService,
    private val teamService: TeamService,
    private val settingsService: GameSettingsService
) : ViewModel() {
    private lateinit var game: ActivityGame

    val gameState = MutableLiveData<GameStatus>()
    val remainingTimeSeconds = MutableLiveData<Int>()

    val currentTeam = MutableLiveData<Team>()
    val currentTask = MutableLiveData<GameTask>()
    val currentTeamRoundScore = MutableLiveData<Int>()

    val lastPlayedTeam = MutableLiveData<Team>()

    private var timer: CountDownTimer? = null

    override fun onCleared() {
        stopTimer()
    }

    fun startNewGame() {
        gameService.startNewGame()
        prepare()
    }

    fun prepare() {
        stopTimer()
        game = gameService.getSavedGame()
        val teams = teams()
        if (game.currentTeamIndex >= teams.size) {
            game.resetCurrentTeam()
        }
        currentTeam.value = teams()[game.currentTeamIndex]
        gameState.value = GameStatus.START
        updateCurrentTeamRoundScore()
    }

    fun completeTask() {
        if (!game.roundIsPlaying) return
        currentTask.value = game.completeCurrentTask()
        updateCurrentTeamRoundScore()
    }

    fun skipTask() {
        if (!game.roundIsPlaying) return
        currentTask.value = game.skipCurrentTask()
        updateCurrentTeamRoundScore()
    }

    fun startRound() {
        currentTask.value = game.startRound()
        gameState.value = GameStatus.PLAY
        startTimer()
    }

    fun lastPlayedTeamScore(): Int {
        if (lastPlayedTeam.value == null) return 0
        return game
            .getTeamCompletedTasks(lastPlayedTeam.value!!.index)
            .totalScoreForLastRound()
    }

    fun gameSettings(): GameSettings {
        return game.settings
    }

    fun finishRound() {
        doFinishRound()
        stopTimer()
    }

    private fun doFinishRound() {
        game.finishRound()
        lastPlayedTeam.value = currentTeam.value
        currentTeam.value = teams()[game.currentTeamIndex]
        gameState.value = GameStatus.FINISH
        gameService.saveGame(game)
    }

    fun nextRoundFrame() {
        gameState.value = GameStatus.START
    }

    fun currentRound(): Int {
        return game.currentRoundIndex + 1
    }

    /**
     * @return list of teams with their scores sorted by playing order
     */
    fun getTeamsBoardItemData(): List<TeamBoardItemData> {
        val teams = teams()
        return (0 until game.settings.teamCount)
            .map {
                val totalScore = game.getTeamTotalScore(it)
                TeamBoardItemData(teams[it], totalScore, it == game.currentTeamIndex)
            }
            .toList()
    }

    fun isGameFinished() = game.finished

    private fun startTimer() {
        stopTimer()
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
                doFinishRound()
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

    private fun teams() = teamService.getTeams()
}