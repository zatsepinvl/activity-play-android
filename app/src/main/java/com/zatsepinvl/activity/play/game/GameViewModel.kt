package com.zatsepinvl.activity.play.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zatsepinvl.activity.play.core.ActivityGame
import com.zatsepinvl.activity.play.core.GameSettings
import com.zatsepinvl.activity.play.core.GameTask


data class Team(
    val index: Int,
    val name: String
)

class GameViewModel(application: Application) :
    AndroidViewModel(application) {

    private val teams = listOf(Team(0, "Team A"), Team(1, "Team B"))
    private val game: ActivityGame
    val currentTask = MutableLiveData<GameTask>()
    val isPlayingRound = MutableLiveData<Boolean>()
    val currentTeam = MutableLiveData<Team>()
    val lastPlayedTeam = MutableLiveData<Team>()

    init {
        val dictionary = application.assets.dictionary(WordFile("words/words_en.txt"))
        game = ActivityGame(
            GameSettings(),
            dictionary
        )
        currentTeam.value = teams[game.currentTeamIndex]
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
    }

    fun finishRound() {
        game.finishRound()
        isPlayingRound.value = false
        lastPlayedTeam.value = currentTeam.value
        currentTeam.value = teams[game.currentTeamIndex]
    }

}
