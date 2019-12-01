package com.zatsepinvl.activity.play.game.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zatsepinvl.activity.play.core.*
import com.zatsepinvl.activity.play.WordFile
import com.zatsepinvl.activity.play.dictionary


data class Team(
    val index: Int,
    val name: String
)

enum class GameState {
    START,
    PLAY,
    FINISH
}

class GameViewModel(application: Application) :
    AndroidViewModel(application) {

    private val teams = listOf(
        Team(0, "Team A"),
        Team(1, "Team B")
    )
    private val game: ActivityGame
    val currentTask = MutableLiveData<GameTask>()
    val isPlayingRound = MutableLiveData<Boolean>()
    val currentTeam = MutableLiveData<Team>()
    val lastPlayedTeam = MutableLiveData<Team>()
    val gameState = MutableLiveData<GameState>()

    init {
        val dictionary = application.assets.dictionary(WordFile("words/words_en.txt"))
        game = ActivityGame(
            GameSettings(),
            dictionary
        )
        currentTeam.value = teams[game.currentTeamIndex]
        gameState.value = GameState.START
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
        gameState.value = GameState.PLAY
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
        gameState.value = GameState.FINISH
    }

    fun nextTeam() {
        gameState.value = GameState.START
    }

}
