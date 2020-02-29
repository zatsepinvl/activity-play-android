package com.zatsepinvl.activity.play.home.viewmodel

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.game.service.GameService
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val gameService: GameService
) : ViewModel() {

    fun canContinueGame(): Boolean = gameService.isGameSaved()
}