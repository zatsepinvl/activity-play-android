package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activityplay.android.fragment.disableBackButton
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.android.onClick
import com.zatsepinvl.activityplay.core.model.GameAction.*
import com.zatsepinvl.activityplay.databinding.FragmentRoundPlayBinding
import com.zatsepinvl.activityplay.effects.EffectsService
import com.zatsepinvl.activityplay.game.fragment.PlayRoundFragmentDirections.Companion.askLastWord
import com.zatsepinvl.activityplay.game.fragment.PlayRoundFragmentDirections.Companion.canvas
import com.zatsepinvl.activityplay.game.viewmodel.RoundGameViewModel
import com.zatsepinvl.activityplay.game.viewmodel.RoundUiViewModel
import com.zatsepinvl.activityplay.game.viewmodel.TimerViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PlayRoundFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val roundGameViewModel: RoundGameViewModel by activityViewModels { viewModelFactory }
    private val timerViewModel: TimerViewModel by activityViewModels { viewModelFactory }
    private val uiViewModel: RoundUiViewModel by activityViewModels { viewModelFactory }

    @Inject
    lateinit var effectsService: EffectsService

    override fun onCreate(savedInstanceState: Bundle?) {
        disableBackButton()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        if (!roundGameViewModel.isPlaying) {
            roundGameViewModel.startRound()
            timerViewModel.startRoundTimer()
        }

        val dataBinding = FragmentRoundPlayBinding.inflate(inflater, root, false)
        dataBinding.gameViewmodel = roundGameViewModel
        dataBinding.timerViewmodel = timerViewModel
        dataBinding.uiViewmodel = uiViewModel

        dataBinding.lifecycleOwner = this

        val hideDrawButton = { dataBinding.gameFrameDrawButton.visibility = View.GONE }
        val showDrawButton = { dataBinding.gameFrameDrawButton.visibility = View.VISIBLE }

        roundGameViewModel.currentTask.observe(viewLifecycleOwner) { task ->
            when (task.action) {
                SHOW -> hideDrawButton()
                SAY -> hideDrawButton()
                DRAW -> showDrawButton()
            }
        }

        timerViewModel.timerFinishedEvent.observe(viewLifecycleOwner) { stopRound() }

        dataBinding.apply {
            gameFrameDoneButton.onClick {
                roundGameViewModel.completeTask()
                effectsService.playPlusCoinTrack()
                effectsService.vibrate()
            }
            gameFrameSkipButton.onClick {
                roundGameViewModel.skipTask()
                effectsService.vibrate()
            }
            gameFrameDrawButton.onClick { navigate(canvas()) }
            gameFrameFinishButton.onClick {
                timerViewModel.stopTimer()
                stopRound()
            }
        }

        return dataBinding.root
    }

    private fun stopRound() {
        effectsService.playTimeIsOverTrack()
        navigate(askLastWord())
    }
}
