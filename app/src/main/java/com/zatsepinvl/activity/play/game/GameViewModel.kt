package com.zatsepinvl.activity.play.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.core.ActivityGame
import com.zatsepinvl.activity.play.core.model.GameTask
import com.zatsepinvl.activity.play.core.totalScoreForLastRound
import com.zatsepinvl.activity.play.game.model.GameStatus
import com.zatsepinvl.activity.play.game.model.TeamBoardItemData
import com.zatsepinvl.activity.play.team.TeamService
import com.zatsepinvl.activity.play.team.model.Team
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val gameService: GameService,
    private val teamService: TeamService
) : ViewModel() {
    private lateinit var game: ActivityGame

    val currentTask = MutableLiveData<GameTask>()
    val isPlayingRound = MutableLiveData<Boolean>()
    val currentTeam = MutableLiveData<Team>()
    val lastPlayedTeam = MutableLiveData<Team>()
    val currentTeamRoundScore = MutableLiveData<Int>()
    val gameState = MutableLiveData<GameStatus>()

    fun startNewGame() {
        gameService.startNewGame()
        prepare()
    }

    fun prepare() {
        game = gameService.getSavedGame()
        currentTeam.value = teams()[game.currentTeamIndex]
        gameState.value = GameStatus.START
        updateCurrentTeamRoundScore()
    }

    fun completeTask() {
        currentTask.value = game.completeCurrentTask()
        updateCurrentTeamRoundScore()
    }

    fun skipTask() {
        currentTask.value = game.skipCurrentTask()
        updateCurrentTeamRoundScore()
    }

    fun startRound() {
        currentTask.value = game.startRound()
        isPlayingRound.value = true
        gameState.value = GameStatus.PLAY
        updateCurrentTeamRoundScore()
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
            .sortedByDescending { it.totalScore }
            .toList()
    }

    fun isGameFinished() = game.finished

    private fun updateCurrentTeamRoundScore() {
        currentTeamRoundScore.value = game.getTeamRoundScore(
            game.currentTeamIndex,
            game.currentRoundIndex
        )
    }

    private fun teams() = teamService.getTeams()
}
