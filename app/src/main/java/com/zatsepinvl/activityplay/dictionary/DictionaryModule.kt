package com.zatsepinvl.activityplay.dictionary

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DictionaryModule {
    @Binds
    @Singleton
    abstract fun dictionaryService(service: DictionaryHolderImpl): DictionaryHolder
}