package com.zatsepinvl.activityplay.gamestate

import com.zatsepinvl.activityplay.gamestate.repository.GameStateRepository
import com.zatsepinvl.activityplay.gamestate.repository.LocalGameStateRepository
import com.zatsepinvl.activityplay.gamestate.service.GameStateService
import com.zatsepinvl.activityplay.gamestate.service.GameStateServiceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class GameStateModule {
    @Binds
    @Singleton
    abstract fun gameStateRepository(repo: LocalGameStateRepository): GameStateRepository

    @Binds
    @Singleton
    abstract fun gameService(service: GameStateServiceImpl): GameStateService
}