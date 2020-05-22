package com.zatsepinvl.activityplay.di

import android.content.Context
import com.zatsepinvl.activityplay.ActivityPlayApplication
import com.zatsepinvl.activityplay.dictionary.DictionaryModule
import com.zatsepinvl.activityplay.game.GameModule
import com.zatsepinvl.activityplay.home.HomeModule
import com.zatsepinvl.activityplay.settings.SettingsModule
import com.zatsepinvl.activityplay.team.TeamModule
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
        DictionaryModule::class,
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

