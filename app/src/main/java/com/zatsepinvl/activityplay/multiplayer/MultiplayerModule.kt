package com.zatsepinvl.activityplay.multiplayer

import com.zatsepinvl.activityplay.multiplayer.lobby.MultiplayerLobbyModule
import com.zatsepinvl.activityplay.multiplayer.room.MultiplayerRoomModule
import dagger.Module

@Module(
    includes = [
        MultiplayerLobbyModule::class,
        MultiplayerRoomModule::class
    ]
)
abstract class MultiplayerModule