package com.zatsepinvl.activityplay.game.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.core.ActivityGame
import com.zatsepinvl.activityplay.core.model.GameAction
import com.zatsepinvl.activityplay.core.model.GameSettings
import com.zatsepinvl.activityplay.game.model.TeamBoardItemData
import com.zatsepinvl.activityplay.game.service.GameActionService
import com.zatsepinvl.activityplay.game.service.GameService
import com.zatsepinvl.activityplay.team.model.Team
import com.zatsepinvl.activityplay.team.service.TeamService
import javax.inject.Inject

class StartRoundViewModel @Inject constructor(
    private val gameService: GameService,
    private val teamService: TeamService,
    private val gameActionService: GameActionService
) : ViewModel() {

    private lateinit var game: ActivityGame
    lateinit var currentTeam: Team
        private set

    val actionLocalName: String
        get() = gameActionService.getActionLocalName(game.currentGameAction)

    val actionDrawable: Drawable
        get() = gameActionService.getActionDrawable(game.currentGameAction)

    fun startNewGame() {
        gameService.startNewGame()
        continueGame()
    }

    fun continueGame() {
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

    fun actionLocalName(action:GameAction):String {
        return gameActionService.getActionLocalName(action)
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