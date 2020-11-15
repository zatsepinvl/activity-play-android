package com.zatsepinvl.activityplay.di.viewmodel

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilder {
    @Binds
    abstract fun viewModelProviderFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

