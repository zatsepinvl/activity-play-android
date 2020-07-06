package com.zatsepinvl.activityplay.team

import com.zatsepinvl.activityplay.team.service.LocalTeamRepository
import com.zatsepinvl.activityplay.team.service.TeamRepository
import com.zatsepinvl.activityplay.team.service.TeamService
import com.zatsepinvl.activityplay.team.service.TeamServiceImpl
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