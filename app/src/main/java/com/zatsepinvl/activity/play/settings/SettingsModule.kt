package com.zatsepinvl.activity.play.settings

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class SettingsModule {
    @Binds
    @Singleton
    abstract fun gameSettingsService(service: GameSettingsServiceImpl): GameSettingsService

    @Binds
    @Singleton
    abstract fun dictionarySettingsRepository(repo: LocalDictionarySettingsRepository): DictionarySettingsRepository

    @Binds
    @Singleton
    abstract fun gameSettingsRepository(repo: LocalGameSettingsRepository): GameSettingsRepository
}