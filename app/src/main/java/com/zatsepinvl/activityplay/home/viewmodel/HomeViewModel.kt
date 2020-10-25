package com.zatsepinvl.activityplay.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.android.viewmodel.SingleLiveEvent
import com.zatsepinvl.activityplay.gamestate.service.GameStateService
import com.zatsepinvl.activityplay.home.viewmodel.HomeMenuItem.MULTIPLAYER
import javax.inject.Inject


enum class HomeMenuItem {
    NEW_GAME,
    CONTINUE,
    MULTIPLAYER,
    SETTINGS,
    INTRO,
    SUPPORT_DEV,
    GITHUB,
    EMAIL
}

class HomeViewModel @Inject constructor(
    private val gameStateService: GameStateService
) : ViewModel() {

    fun canContinueGame(): Boolean = gameStateService.isGameSaved()

    val _menuItemSelectedEvent = SingleLiveEvent<HomeMenuItem>()
    val menuItemSelectedEvent: LiveData<HomeMenuItem> = _menuItemSelectedEvent

    fun setupMultiPlayerGame() {
        _menuItemSelectedEvent.call(MULTIPLAYER)
    }
}