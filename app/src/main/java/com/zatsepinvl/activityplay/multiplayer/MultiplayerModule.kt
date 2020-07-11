package com.zatsepinvl.activityplay.multiplayer

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.di.ViewModelBuilder
import com.zatsepinvl.activityplay.di.ViewModelKey
import com.zatsepinvl.activityplay.multiplayer.lobby.fragment.MultiplayerLobbyFragment
import com.zatsepinvl.activityplay.multiplayer.lobby.viewmodel.MultiplayerLobbyViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MultiplayerModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun multiplayerLobbyFragment(): MultiplayerLobbyFragment

    @Binds
    @IntoMap
    @ViewModelKey(MultiplayerLobbyViewModel::class)
    abstract fun multiplayerLobbyViewModel(multiplayerLobbyViewModel: MultiplayerLobbyViewModel): ViewModel
}