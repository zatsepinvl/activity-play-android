package com.zatsepinvl.activity.play.team

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.di.ViewModelBuilder
import com.zatsepinvl.activity.play.di.ViewModelKey
import com.zatsepinvl.activity.play.team.fragment.AddNewTeamDialogFragment
import com.zatsepinvl.activity.play.team.fragment.TeamListFragment
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
    abstract fun addNewTeamDialogFragment(): AddNewTeamDialogFragment

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