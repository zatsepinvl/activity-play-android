package com.zatsepinvl.activityplay.home

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.di.ViewModelBuilder
import com.zatsepinvl.activityplay.di.ViewModelKey
import com.zatsepinvl.activityplay.home.fragment.HomeFragment
import com.zatsepinvl.activityplay.home.fragment.SplashScreenFragment
import com.zatsepinvl.activityplay.home.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun splashScreenFragment(): SplashScreenFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel
}