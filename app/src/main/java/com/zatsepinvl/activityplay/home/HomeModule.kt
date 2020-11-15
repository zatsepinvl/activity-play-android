package com.zatsepinvl.activityplay.home

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.di.viewmodel.ViewModelBuilder
import com.zatsepinvl.activityplay.di.viewmodel.ViewModelKey
import com.zatsepinvl.activityplay.home.fragment.HomeFragment
import com.zatsepinvl.activityplay.home.fragment.SplashScreenFragment
import com.zatsepinvl.activityplay.home.viewmodel.HomeMenuViewModel
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
    @ViewModelKey(HomeMenuViewModel::class)
    abstract fun homeViewModel(homeMenuViewModel: HomeMenuViewModel): ViewModel
}