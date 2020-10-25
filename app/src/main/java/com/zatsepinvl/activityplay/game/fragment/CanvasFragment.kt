package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.databinding.FragmentCanvasBinding
import com.zatsepinvl.activityplay.effects.EffectsService
import com.zatsepinvl.activityplay.game.viewmodel.RoundGameViewModel
import com.zatsepinvl.activityplay.game.viewmodel.TimerViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CanvasFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val roundGameViewModel: RoundGameViewModel by activityViewModels { viewModelFactory }
    private val timerViewModel: TimerViewModel by activityViewModels { viewModelFactory }

    @Inject
    lateinit var effectsService: EffectsService

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        val binding = FragmentCanvasBinding.inflate(inflater, root, false)
        binding.lifecycleOwner = this
        binding.apply {
            playViewmodel = roundGameViewModel
            timerViewmodel = timerViewModel
            canvasBackButton.onClick { findNavController().popBackStack() }
            canvasClearButton.onClick { canvasPaintView.clear() }
        }

        timerViewModel.timerFinishedEvent.observe(viewLifecycleOwner, Observer {
            effectsService.playTimeIsOverTrack()
            navigate(CanvasFragmentDirections.askLastWord())
        })

        return binding.root
    }
}