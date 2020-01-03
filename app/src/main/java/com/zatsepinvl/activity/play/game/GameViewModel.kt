package com.zatsepinvl.activity.play.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.color.ColorService
import com.zatsepinvl.activity.play.core.ActivityGame
import com.zatsepinvl.activity.play.core.GameTask
import com.zatsepinvl.activity.play.core.totalScoreForLastRound
import com.zatsepinvl.activity.play.team.Team
import com.zatsepinvl.activity.play.team.TeamService
import javax.inject.Inject

enum class GameStatus {
    START,
    PLAY,
    FINISH
}

class GameViewModel @Inject constructor(
    private val gameService: GameService,
    private val teamService: TeamService,
    private val colorService: ColorService
) : ViewModel() {
    private lateinit var game: ActivityGame

    val currentTask = MutableLiveData<GameTask>()
    val isPlayingRound = MutableLiveData<Boolean>()
    val currentTeam = MutableLiveData<Team>()
    val lastPlayedTeam = MutableLiveData<Team>()
    val gameState = MutableLiveData<GameStatus>()

    init {
        reloadGame()
    }

    fun reloadGame() {
        game = gameService.getSavedGame()
        currentTeam.value = teams()[game.currentTeamIndex]
        gameState.value = GameStatus.START
    }

    fun completeTask() {
        currentTask.value = game.completeCurrentTask()
    }

    fun skipTask() {
        currentTask.value = game.skipCurrentTask()
    }

    fun startRound() {
        currentTask.value = game.startRound()
        isPlayingRound.value = true
        gameState.value = GameStatus.PLAY
    }

    fun lastPlayedTeamScore(): Int {
        if (lastPlayedTeam.value == null) return 0
        return game
            .getTeamCompletedTasks(lastPlayedTeam.value!!.index)
            .totalScoreForLastRound()
    }

    fun finishRound() {
        game.finishRound()
        isPlayingRound.value = false
        lastPlayedTeam.value = currentTeam.value
        currentTeam.value = teams()[game.currentTeamIndex]
        gameState.value = GameStatus.FINISH
        gameService.saveGame(game)
    }

    fun nextTeam() {
        gameState.value = GameStatus.START
    }

    fun currentTeamTotalScore(): Int {
        return game.getTeamTotalScore(currentTeam.value!!.index)
    }

    fun currentTeamColorResource(): Int {
        return colorService.getColorResourceById(currentTeam.value!!.colorId)
    }

    fun isGameFinished() = game.finished

    private fun teams() = teamService.getTeams()
}
