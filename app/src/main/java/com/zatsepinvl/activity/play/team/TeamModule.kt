package com.zatsepinvl.activity.play.team

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class TeamModule {
    @Binds
    @Singleton
    abstract fun teamRepository(repository: LocalTeamRepository): TeamRepository

    @Binds
    @Singleton
    abstract fun teamService(service: TeamServiceImpl): TeamService
}