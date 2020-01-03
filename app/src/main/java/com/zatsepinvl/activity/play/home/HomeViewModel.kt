package com.zatsepinvl.activity.play.home

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.game.GameService
import com.zatsepinvl.activity.play.team.TeamService
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val gameService: GameService,
    private val teamService: TeamService
) : ViewModel() {

    fun initNewGame() {
        val newGame = gameService.createNewGame()
        gameService.saveGame(newGame)
    }

    fun canContinueGame(): Boolean = gameService.isGameSaved()
}