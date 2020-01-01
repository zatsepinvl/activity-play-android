package com.zatsepinvl.activity.play.settings

import android.content.Context
import com.zatsepinvl.activity.play.game.DictionarySettings
import com.zatsepinvl.activity.play.android.LocalJsonObjectRepository
import com.zatsepinvl.activity.play.android.ObjectRepository
import javax.inject.Inject

interface DictionarySettingsRepository :
    ObjectRepository<DictionarySettings>

private const val SP_SETTINGS_NAME = "DictionarySettings"

class LocalDictionarySettingsRepository @Inject constructor(context: Context) :
    DictionarySettingsRepository,
    LocalJsonObjectRepository<DictionarySettings>(
        context,
        SP_SETTINGS_NAME,
        DictionarySettings::class.java
    )

