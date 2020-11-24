package com.zatsepinvl.activityplay.game.viewmodel

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.game.model.TeamResult
import com.zatsepinvl.activityplay.gameroom.service.GameRoomManager
import javax.inject.Inject

class GameFinishViewModel @Inject constructor(
    private val roomManager: GameRoomManager
) : ViewModel() {

    fun getTeamResults(): List<TeamResult> {
        val game = roomManager.currentGame
        return roomManager.teams
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