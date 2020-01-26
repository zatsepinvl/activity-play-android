package com.zatsepinvl.activity.play.game

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.game.model.TeamResult
import javax.inject.Inject

class FinishGameViewModel @Inject constructor(
    private val gameService: GameService
) : ViewModel() {

    fun getTeamResults(): List<TeamResult> {
        val game = gameService.getSavedGame()
        return (0 until game.settings.teamCount)
            .map {
                val totalScore = game.getTeamTotalScore(it)
                val winner = game.getWinnerTeamIndex() == it
                TeamResult("Team #$it", totalScore, winner)
            }
            .sortedByDescending { it.totalScore }
            .toList()
    }
}