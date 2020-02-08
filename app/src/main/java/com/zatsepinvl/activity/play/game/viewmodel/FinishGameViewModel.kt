package com.zatsepinvl.activity.play.game.viewmodel

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.game.model.TeamResult
import com.zatsepinvl.activity.play.game.service.GameService
import com.zatsepinvl.activity.play.team.service.TeamService
import javax.inject.Inject

class FinishGameViewModel @Inject constructor(
    private val gameService: GameService,
    private val teamService: TeamService
) : ViewModel() {

    fun getTeamResults(): List<TeamResult> {
        val game = gameService.getSavedGame()
        val teams = teamService.getTeams().map {
            it to game.getTeamTotalScore(it.index)
        }.sortedByDescending { it.second }
        return teams.indices.map {
            val totalScore = teams[it].second
            val team = teams[it].first
            val winner = game.getWinnerTeamIndex() == team.index
            TeamResult(
                team = team,
                position = it + 1,
                totalScore = totalScore,
                winner = winner
            )
        }
    }
}