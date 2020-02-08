package com.zatsepinvl.activity.play.game.viewmodel

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.core.ActivityGame
import com.zatsepinvl.activity.play.core.totalScoreForLastRound
import com.zatsepinvl.activity.play.game.service.GameService
import com.zatsepinvl.activity.play.team.model.Team
import javax.inject.Inject

class FinishRoundViewModel @Inject constructor(
    private val gameService: GameService
) : ViewModel() {

    private lateinit var game: ActivityGame
    lateinit var currentTeam: Team
        private set

    fun start() {
        currentTeam = gameService.currentTeam()
        game = gameService.getSavedGame()
    }

    fun completeRound() {
        game.nextTeam()
        gameService.saveGame(game)
    }

    fun currentTeamRoundScore(): Int {
        return game
            .getTeamCompletedTasks(game.currentTeamIndex)
            .totalScoreForLastRound()
    }
}