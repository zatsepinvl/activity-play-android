package com.zatsepinvl.activityplay.gameaction

import dagger.Binds
import dagger.Module

@Module
abstract class GameActionModule {

    @Binds
    abstract fun gameActionService(impl: GameActionServiceImpl): GameActionService
}