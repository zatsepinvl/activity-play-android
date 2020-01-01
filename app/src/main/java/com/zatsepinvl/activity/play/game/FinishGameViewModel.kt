package com.zatsepinvl.activity.play.game

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.core.ActivityGame
import javax.inject.Inject

data class TeamResult(
    val name: String,
    val totalScore: Int,
    val winner: Boolean
)

class FinishGameViewModel @Inject constructor(gameService: GameService) : ViewModel() {

    private val game: ActivityGame = gameService.getSavedGame()

    fun getTeamResults(): List<TeamResult> {
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