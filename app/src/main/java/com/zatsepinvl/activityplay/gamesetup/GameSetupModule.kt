package com.zatsepinvl.activityplay.gamesetup

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.di.viewmodel.ViewModelBuilder
import com.zatsepinvl.activityplay.di.viewmodel.ViewModelKey
import com.zatsepinvl.activityplay.gamesetup.fragment.TeamsSetupFragment
import com.zatsepinvl.activityplay.gamesetup.fragment.UpdateTeamDialogFragment
import com.zatsepinvl.activityplay.gamesetup.viewmodel.GameSetupViewModel
import com.zatsepinvl.activityplay.gamesetup.viewmodel.TeamsSetupViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class GameSetupModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun teamSettingsFragment(): TeamsSetupFragment

    @ContributesAndroidInjector
    abstract fun addNewTeamDialogFragment(): UpdateTeamDialogFragment

    @Binds
    @IntoMap
    @ViewModelKey(TeamsSetupViewModel::class)
    abstract fun teamsSetupViewModel(viewModel: TeamsSetupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameSetupViewModel::class)
    abstract fun gameSetupViewModel(viewModel: GameSetupViewModel): ViewModel
}