package com.zatsepinvl.activityplay.multiplayer.room

import com.zatsepinvl.activityplay.multiplayer.room.service.MultiplayerRoomService
import com.zatsepinvl.activityplay.multiplayer.room.service.MultiplayerRoomServiceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MultiplayerRoomModule {
    @Binds
    abstract fun multiplayerRoomService(
        multiplayerRoomServiceImpl: MultiplayerRoomServiceImpl
    ): MultiplayerRoomService
}