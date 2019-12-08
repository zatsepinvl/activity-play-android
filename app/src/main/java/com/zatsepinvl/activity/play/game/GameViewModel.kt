package com.zatsepinvl.activity.play.game

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.core.*
import javax.inject.Inject


data class Team(
    val index: Int,
    val name: String
)

enum class GameStatus {
    START,
    PLAY,
    FINISH
}

class GameViewModel @Inject constructor(private val gameService: GameService) : ViewModel() {
    private val teams = listOf(
        Team(0, "Team A"),
        Team(1, "Team B")
    )

    private val game: ActivityGame

    val currentTask = MutableLiveData<GameTask>()
    val isPlayingRound = MutableLiveData<Boolean>()
    val currentTeam = MutableLiveData<Team>()
    val lastPlayedTeam = MutableLiveData<Team>()
    val gameState = MutableLiveData<GameStatus>()

    init {
        game = gameService.getLastSavedGame()
        currentTeam.value = teams[game.currentTeamIndex]
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

    fun lastPlayedTeamScore(): String {
        return game
            .getTeamCompletedTasks(lastPlayedTeam.value!!.index)
            .totalScoreForLastRound().toString()
    }

    fun finishRound() {
        game.finishRound()
        isPlayingRound.value = false
        lastPlayedTeam.value = currentTeam.value
        currentTeam.value = teams[game.currentTeamIndex]
        gameState.value = GameStatus.FINISH
        gameService.saveGame(game)
    }

    fun nextTeam() {
        gameState.value = GameStatus.START
    }

}
