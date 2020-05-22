package com.zatsepinvl.activityplay.home.viewmodel

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.game.service.GameService
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val gameService: GameService
) : ViewModel() {

    fun canContinueGame(): Boolean = gameService.isGameSaved()
}