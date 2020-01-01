package com.zatsepinvl.activity.play.settings

import android.content.Context
import com.zatsepinvl.activity.play.core.GameSettings
import com.zatsepinvl.activity.play.android.LocalJsonObjectRepository
import com.zatsepinvl.activity.play.android.ObjectRepository
import javax.inject.Inject

interface GameSettingsRepository :
    ObjectRepository<GameSettings>

private const val GAME_SETTINGS_SP_NAME = "GameSettings"

class LocalGameSettingsRepository @Inject constructor(context: Context) :
    GameSettingsRepository,
    LocalJsonObjectRepository<GameSettings>(
        context,
        GAME_SETTINGS_SP_NAME,
        GameSettings::class.java
    )

