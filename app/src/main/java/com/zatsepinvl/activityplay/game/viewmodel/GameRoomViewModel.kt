package com.zatsepinvl.activityplay.game.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.android.viewmodel.SingleLiveEvent
import com.zatsepinvl.activityplay.core.ActivityGame
import com.zatsepinvl.activityplay.core.model.GameSettings
import com.zatsepinvl.activityplay.game.model.TeamBoardItemData
import com.zatsepinvl.activityplay.gameaction.GameActionService
import com.zatsepinvl.activityplay.gameroom.service.GameRoomManager
import com.zatsepinvl.activityplay.settings.service.GameSettingsService
import com.zatsepinvl.activityplay.team.model.Team
import javax.inject.Inject

class GameRoomViewModel @Inject constructor(
    private val gameActionService: GameActionService,
    private val roomManager: GameRoomManager,
    private val gameSettingsService: GameSettingsService
) : ViewModel() {

    private lateinit var game: ActivityGame

    val currentTeam = MutableLiveData<Team>(null)

    val actionLocalName: String
        get() = gameActionService.getActionLocalName(game.currentGameAction)

    val actionDrawable: Drawable
        get() = gameActionService.getActionDrawable(game.currentGameAction)

    fun setupGame() {
        roomManager.updateGameSettings(gameSettingsService.getGameSettings())
        game = roomManager.currentGame
        val teams = roomManager.teams
        if (game.currentTeamIndex >= teams.size) {
            game.resetCurrentTeam()
            roomManager.updateGame(game)
        }
        currentTeam.value = roomManager.currentTeam
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
        val teams = roomManager.teams
        return (0 until game.settings.teamCount)
            .map {
                val totalScore = game.getTeamTotalScore(it)
                TeamBoardItemData(teams[it], totalScore, it == game.currentTeamIndex)
            }
            .toList()
    }

    fun isGameFinished() = game.finished
}