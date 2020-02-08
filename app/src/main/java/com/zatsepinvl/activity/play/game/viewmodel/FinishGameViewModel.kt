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
        return teamService.getTeams()
            .map {
                val totalScore = game.getTeamTotalScore(it.index)
                TeamResult(
                    team = it,
                    position = 0,
                    totalScore = totalScore,
                    winner = false
                )
            }
            .sortedByDescending { it.totalScore }
            .mapIndexed { index, teamResult ->
                teamResult.copy(
                    position = index + 1,
                    winner = teamResult.team.index == game.getWinnerTeamIndex()
                )
            }
    }
}