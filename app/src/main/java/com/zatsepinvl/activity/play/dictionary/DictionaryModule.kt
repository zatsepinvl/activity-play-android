package com.zatsepinvl.activity.play.dictionary

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DictionaryModule {
    @Binds
    @Singleton
    abstract fun dictionaryService(service: DictionaryServiceImpl): DictionaryService
}