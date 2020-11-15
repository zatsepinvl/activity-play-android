package com.zatsepinvl.activityplay.gameroom.di

import com.zatsepinvl.activityplay.gameroom.model.GameRoomMode
import dagger.MapKey
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*

@Target(FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER)
@Retention(RUNTIME)
@MapKey
annotation class GameModeKey(val value: GameRoomMode)