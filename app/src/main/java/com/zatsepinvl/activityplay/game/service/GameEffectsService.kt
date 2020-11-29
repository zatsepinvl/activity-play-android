package com.zatsepinvl.activityplay.game.service

interface GameEffectsService {
    fun onRoundStarted()
    fun onTaskCompleted()
    fun onTaskFailed()
    fun onRoundFinished()
    fun onTimeIsOver()
    fun onGameFinished()
}


