package com.zatsepinvl.activityplay.gameroom

import com.zatsepinvl.activityplay.gameroom.service.*
import dagger.Binds
import dagger.Module

@Module
abstract class GameRoomModule {

    @Binds
    abstract fun gameRoomService(impl: GameRoomManagerImpl): GameRoomManager

    @Binds
    abstract fun gameFactory(impl: GameFactoryImpl): GameFactory
}