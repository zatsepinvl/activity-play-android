package com.zatsepinvl.activityplay.multiplayer

import com.zatsepinvl.activityplay.di.ViewModelBuilder
import com.zatsepinvl.activityplay.multiplayer.fragment.MultiplayerLobbyFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MultiplayerModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun multiplayerLobbyFragment(): MultiplayerLobbyFragment
}