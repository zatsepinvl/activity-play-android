package com.zatsepinvl.activity.play.game.viewmodel

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.core.ActivityGame
import com.zatsepinvl.activity.play.core.model.GameSettings
import com.zatsepinvl.activity.play.game.model.TeamBoardItemData
import com.zatsepinvl.activity.play.game.service.GameService
import com.zatsepinvl.activity.play.team.model.Team
import com.zatsepinvl.activity.play.team.service.TeamService
import javax.inject.Inject

class StartRoundViewModel @Inject constructor(
    private val gameService: GameService,
    private val teamService: TeamService
) : ViewModel() {

    private lateinit var game: ActivityGame
    lateinit var currentTeam: Team
        private set

    fun startNewGame() {
        gameService.startNewGame()
        start()
    }

    fun start() {
        game = gameService.getSavedGame()
        val teams = teams()
        if (game.currentTeamIndex >= teams.size) {
            game.resetCurrentTeam()
            gameService.saveGame(game)
        }
        currentTeam = gameService.currentTeam()
    }

    fun gameSettings(): GameSettings {
        return game.settings
    }

    fun currentRound(): Int {
        return game.currentRoundIndex + 1
    }

    /**
     * @return list of teams with their scores sorted by playing order
     */
    fun getTeamsBoardItemData(): List<TeamBoardItemData> {
        val teams = teams()
        return (0 until game.settings.teamCount)
            .map {
                val totalScore = game.getTeamTotalScore(it)
                TeamBoardItemData(teams[it], totalScore, it == game.currentTeamIndex)
            }
            .toList()
    }

    fun isGameFinished() = game.finished

    private fun teams() = teamService.getTeams()
}