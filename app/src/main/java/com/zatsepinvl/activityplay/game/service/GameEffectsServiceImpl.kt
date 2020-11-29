package com.zatsepinvl.activityplay.game.service

import com.zatsepinvl.activityplay.effects.EffectsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameEffectsServiceImpl @Inject constructor(
    private val effectsService: EffectsService
) : GameEffectsService {

    override fun onRoundStarted() {
        effectsService.playGrandOpeningTrack()
    }

    override fun onTaskCompleted() {
        effectsService.playPlusCoinTrack()
        effectsService.vibrate()
    }

    override fun onTaskFailed() {
        effectsService.vibrate()
    }

    override fun onRoundFinished() {
        effectsService.playFlowerTrack()
        effectsService.vibrate()
    }

    override fun onTimeIsOver() {
        effectsService.playRootsTrack()
        effectsService.vibrate()
    }

    override fun onGameFinished() {
        effectsService.playGameOverTrack()
    }

}