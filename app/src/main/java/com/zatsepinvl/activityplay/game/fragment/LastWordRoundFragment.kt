package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activityplay.android.fragment.disableBackButton
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.android.onClick
import com.zatsepinvl.activityplay.databinding.FragmentRoundLastWordBinding
import com.zatsepinvl.activityplay.game.viewmodel.PlayRoundViewModel
import com.zatsepinvl.activityplay.game.viewmodel.TimerViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LastWordRoundFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val playViewModel: PlayRoundViewModel by activityViewModels { viewModelFactory }
    private val timerViewModel: TimerViewModel by activityViewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        disableBackButton()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        val binding = FragmentRoundLastWordBinding.inflate(inflater, root, false)
        binding.lifecycleOwner = this
        binding.apply {
            playViewmodel = playViewModel
            timerViewmodel = timerViewModel
            roundLastWordNoButton.onClick { skipLastWord() }
            roundLastWordYesButton.onClick { completeLastWord() }
        }

        timerViewModel.timerFinishedEvent.observe(viewLifecycleOwner, Observer { skipLastWord() })
        timerViewModel.startLastWordTimer()

        return binding.root
    }

    private fun completeLastWord() {
        playViewModel.completeTask()
        finishRound()
    }

    private fun skipLastWord() {
        playViewModel.skipTask()
        finishRound()
    }

    private fun finishRound() {
        timerViewModel.stopTimer()
        navigate(LastWordRoundFragmentDirections.finishRound())
    }
}
