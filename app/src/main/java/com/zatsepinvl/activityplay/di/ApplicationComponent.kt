package com.zatsepinvl.activityplay.di

import android.content.Context
import com.zatsepinvl.activityplay.ActivityPlayApplication
import com.zatsepinvl.activityplay.dictionary.DictionaryModule
import com.zatsepinvl.activityplay.game.GameModule
import com.zatsepinvl.activityplay.gamesetup.GameSetupModule
import com.zatsepinvl.activityplay.home.HomeModule
import com.zatsepinvl.activityplay.multiplayer.MultiplayerModule
import com.zatsepinvl.activityplay.settings.SettingsModule
import com.zatsepinvl.activityplay.team.TeamModule
import com.zatsepinvl.activityplay.timer.TimerModule
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
        TeamModule::class,
        HomeModule::class,
        GameSetupModule::class,
        TimerModule::class,
        MultiplayerModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<ActivityPlayApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}

