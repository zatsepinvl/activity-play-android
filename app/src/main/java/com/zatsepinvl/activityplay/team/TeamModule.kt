package com.zatsepinvl.activityplay.team

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.di.ViewModelBuilder
import com.zatsepinvl.activityplay.di.ViewModelKey
import com.zatsepinvl.activityplay.team.fragment.TeamListFragment
import com.zatsepinvl.activityplay.team.fragment.UpdateTeamDialogFragment
import com.zatsepinvl.activityplay.team.service.LocalTeamRepository
import com.zatsepinvl.activityplay.team.service.TeamRepository
import com.zatsepinvl.activityplay.team.service.TeamService
import com.zatsepinvl.activityplay.team.service.TeamServiceImpl
import com.zatsepinvl.activityplay.team.viewmodel.TeamSettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class TeamModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun teamSettingsFragment(): TeamListFragment

    @ContributesAndroidInjector
    abstract fun addNewTeamDialogFragment(): UpdateTeamDialogFragment

    @Binds
    @Singleton
    abstract fun teamRepository(repository: LocalTeamRepository): TeamRepository

    @Binds
    @Singleton
    abstract fun teamService(service: TeamServiceImpl): TeamService

    @Binds
    @IntoMap
    @ViewModelKey(TeamSettingsViewModel::class)
    abstract fun teamSettingsViewModel(teamSettingsViewModel: TeamSettingsViewModel): ViewModel
}