package com.zatsepinvl.activityplay.settings.model

const val SECONDS_FOR_LAST_WORD = 10

data class TimerSettings(
    val roundTimeSeconds: Int,
    val lastWordSeconds: Int = SECONDS_FOR_LAST_WORD
)