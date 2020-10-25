package com.zatsepinvl.activityplay.settings.service

import com.zatsepinvl.activityplay.core.model.GameSettings
import com.zatsepinvl.activityplay.settings.model.TimerSettings

interface GameSettingsService {
    fun getGameSettings(): GameSettings
    fun getTimerSettings(): TimerSettings
}

