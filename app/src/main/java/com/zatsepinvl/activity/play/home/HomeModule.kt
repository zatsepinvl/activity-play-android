package com.zatsepinvl.activity.play.home

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.di.ViewModelBuilder
import com.zatsepinvl.activity.play.di.ViewModelKey
import com.zatsepinvl.activity.play.game.FinishGameViewModel
import com.zatsepinvl.activity.play.home.fragment.HomeFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun homeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel
}