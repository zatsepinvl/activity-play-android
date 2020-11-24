package com.zatsepinvl.activityplay.game.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.effects.EffectsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameEffectsViewModel @Inject constructor(
    private val effectsService: EffectsService
) : ViewModel() {

    /**
     *  Experiment here: viewModel subscribes to another viewmodel.
     *  Pros:
     *      - No glue code in Fragment.
     *      - Effects can be tested in isolation from fragment.
     *  Cons:
     *      - Haven't seen anywhere so far.
     */
    fun subscribeOnGameEvents(owner: LifecycleOwner, viewModel: GameViewModel) {
        viewModel.roundStartedEvent.observe(owner) {
            onRoundStarted()
        }
        viewModel.taskCompletedEvent.observe(owner) {
            onTaskCompleted()
        }
        viewModel.taskFailedEvent.observe(owner) {
            onTaskFailed()
        }
        viewModel.roundFinishedEvent.observe(owner) {
            onRoundFinished()
        }
    }

    fun onRoundStarted() {
        effectsService.playGrandOpeningTrack()
    }

    fun onTaskCompleted() {
        effectsService.playPlusCoinTrack()
        effectsService.vibrate()
    }

    fun onTaskFailed() {
        effectsService.vibrate()
    }

    fun onRoundFinished() {
        effectsService.playFlowerTrack()
        effectsService.vibrate()
    }

    fun onTimeIsOver() {
        effectsService.playRootsTrack()
        effectsService.vibrate()
    }

    fun onGameFinished() {
        effectsService.playGameOverTrack()
    }
}
