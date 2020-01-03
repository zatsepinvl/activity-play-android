package com.zatsepinvl.activity.play.team

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.di.ViewModelBuilder
import com.zatsepinvl.activity.play.di.ViewModelKey
import com.zatsepinvl.activity.play.game.FinishGameViewModel
import com.zatsepinvl.activity.play.game.fragment.StartRoundFragment
import com.zatsepinvl.activity.play.team.fragment.TeamSettingsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class TeamModule {
    @Binds
    @Singleton
    abstract fun teamRepository(repository: LocalTeamRepository): TeamRepository

    @Binds
    @Singleton
    abstract fun teamService(service: TeamServiceImpl): TeamService

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun teamSettingsFragment(): TeamSettingsFragment

    @Binds
    @IntoMap
    @ViewModelKey(TeamSettingsViewModel::class)
    abstract fun teamSettingsViewModel(teamSettingsViewModel: TeamSettingsViewModel): ViewModel
}