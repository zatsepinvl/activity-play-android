package com.zatsepinvl.activity.play.home

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.game.GameService
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val gameService: GameService
) : ViewModel() {

    fun canContinueGame(): Boolean = gameService.isGameSaved()
}