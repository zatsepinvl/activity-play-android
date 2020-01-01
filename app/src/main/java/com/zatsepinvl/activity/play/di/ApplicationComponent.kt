package com.zatsepinvl.activity.play.di

import android.content.Context
import com.zatsepinvl.activity.play.ActivityPlayApplication
import com.zatsepinvl.activity.play.game.GameModule
import com.zatsepinvl.activity.play.home.HomeModule
import com.zatsepinvl.activity.play.settings.SettingsModule
import com.zatsepinvl.activity.play.team.TeamModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        SettingsModule::class,
        GameModule::class,
        HomeModule::class,
        TeamModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<ActivityPlayApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}

