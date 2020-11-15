package com.zatsepinvl.activityplay.multiplayer.lobby

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.di.viewmodel.ViewModelBuilder
import com.zatsepinvl.activityplay.di.viewmodel.ViewModelKey
import com.zatsepinvl.activityplay.multiplayer.lobby.fragment.MultiplayerLobbyFragment
import com.zatsepinvl.activityplay.multiplayer.lobby.viewmodel.MultiplayerLobbyViewModel
import com.zatsepinvl.activityplay.multiplayer.room.MultiplayerRoomModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [MultiplayerRoomModule::class])
abstract class MultiplayerLobbyModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun multiplayerLobbyFragment(): MultiplayerLobbyFragment

    @Binds
    @IntoMap
    @ViewModelKey(MultiplayerLobbyViewModel::class)
    abstract fun multiplayerLobbyViewModel(multiplayerLobbyViewModel: MultiplayerLobbyViewModel): ViewModel
}