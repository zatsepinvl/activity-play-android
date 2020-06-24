package com.zatsepinvl.activityplay.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.android.viewmodel.Event
import com.zatsepinvl.activityplay.game.service.GameService
import com.zatsepinvl.activityplay.home.viewmodel.HomePageEvent.SETUP_MULTIPLAYER_GAME
import javax.inject.Inject


enum class HomePageEvent {
    SETUP_MULTIPLAYER_GAME
}

class HomeViewModel @Inject constructor(
    private val gameService: GameService
) : ViewModel() {

    fun canContinueGame(): Boolean = gameService.isGameSaved()

    private val _homePageEvent = MutableLiveData<Event<HomePageEvent>>()
    val homePageEvent: LiveData<Event<HomePageEvent>> = _homePageEvent

    fun setupMultiPlayerGame() {
        _homePageEvent.value = Event(SETUP_MULTIPLAYER_GAME)
    }
}