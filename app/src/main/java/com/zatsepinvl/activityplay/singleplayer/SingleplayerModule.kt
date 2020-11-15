package com.zatsepinvl.activityplay.singleplayer

import com.zatsepinvl.activityplay.gameroom.di.GameModeKey
import com.zatsepinvl.activityplay.gameroom.model.GameRoomMode.SINGLEPLAYER
import com.zatsepinvl.activityplay.gameroom.service.GameRoomStateRepository
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SingleplayerModule {
    @Binds
    abstract fun singleplayerGameService(impl: SingleplayerGameServiceImpl): SingleplayerGameService

    @Binds
    @IntoMap
    @GameModeKey(SINGLEPLAYER)
    abstract fun singleplayerRoomStateRepository(impl: SingleplayerGameServiceImpl): GameRoomStateRepository
}