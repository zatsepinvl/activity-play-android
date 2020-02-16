package com.zatsepinvl.activity.play.settings

import com.zatsepinvl.activity.play.di.ViewModelBuilder
import com.zatsepinvl.activity.play.settings.fragment.GameSettingsFragment
import com.zatsepinvl.activity.play.settings.service.GameSettingsService
import com.zatsepinvl.activity.play.settings.service.GameSettingsServiceImpl
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