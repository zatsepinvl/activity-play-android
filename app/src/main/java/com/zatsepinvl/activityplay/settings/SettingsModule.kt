package com.zatsepinvl.activityplay.settings

import com.zatsepinvl.activityplay.di.viewmodel.ViewModelBuilder
import com.zatsepinvl.activityplay.settings.fragment.GameSettingsFragment
import com.zatsepinvl.activityplay.settings.service.GameSettingsService
import com.zatsepinvl.activityplay.settings.service.GameSettingsServiceImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class SettingsModule {
    @Binds
    @Singleton
    abstract fun gameSettingsService(service: GameSettingsServiceImpl): GameSettingsService

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun gameSettingsFragment(): GameSettingsFragment
}