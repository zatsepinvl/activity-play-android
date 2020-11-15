package com.zatsepinvl.activityplay.home.viewmodel

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.android.viewmodel.SingleLiveEvent
import com.zatsepinvl.activityplay.gamestate.service.GameStateService
import com.zatsepinvl.activityplay.home.viewmodel.HomeMenuItem.*
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

class HomeMenuViewModel @Inject constructor(
    private val gameStateService: GameStateService
) : ViewModel() {

    val menuItemSelectedEvent = SingleLiveEvent<HomeMenuItem>()
    val canContinueGame get() = gameStateService.isGameSaved()

    fun setupMultiplayerGame() = menuItemSelectedEvent.call(MULTIPLAYER)
    fun startSingleplayerGame() = menuItemSelectedEvent.call(NEW_GAME)
    fun continueSingleplayerGame() = menuItemSelectedEvent.call(CONTINUE)
    fun goSettings() = menuItemSelectedEvent.call(SETTINGS)
    fun showIntro() = menuItemSelectedEvent.call(INTRO)
    fun supportDev() = menuItemSelectedEvent.call(SUPPORT_DEV)
    fun goGithub() = menuItemSelectedEvent.call(GITHUB)
    fun sendEmail() = menuItemSelectedEvent.call(EMAIL)
}