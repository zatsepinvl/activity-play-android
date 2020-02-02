package com.zatsepinvl.activity.play.game

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.game.model.TeamResult
import com.zatsepinvl.activity.play.team.TeamService
import javax.inject.Inject

class FinishGameViewModel @Inject constructor(
    private val gameService: GameService,
    private val teamService: TeamService
) : ViewModel() {

    fun getTeamResults(): List<TeamResult> {
        val game = gameService.getSavedGame()
        val teams = teamService.getTeams()
        return (0 until game.settings.teamCount)
            .map {
                val totalScore = game.getTeamTotalScore(it)
                val winner = game.getWinnerTeamIndex() == it
                TeamResult(teams[it], totalScore, winner)
            }
            .sortedByDescending { it.totalScore }
            .toList()
    }
}