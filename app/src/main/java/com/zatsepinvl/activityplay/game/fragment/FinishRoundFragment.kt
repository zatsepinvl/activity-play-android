package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activityplay.android.fragment.disableBackButton
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.databinding.FragmentRoundFinishBinding
import com.zatsepinvl.activityplay.effects.EffectsService
import com.zatsepinvl.activityplay.game.fragment.FinishRoundFragmentDirections.Companion.nextRound
import com.zatsepinvl.activityplay.game.viewmodel.PlayRoundViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FinishRoundFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PlayRoundViewModel by activityViewModels { viewModelFactory }

    @Inject
    lateinit var effectsService: EffectsService

    override fun onCreate(savedInstanceState: Bundle?) {
        disableBackButton()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        val binding = FragmentRoundFinishBinding.inflate(inflater, root, false)
        binding.lifecycleOwner = this
        binding.apply {
            viewmodel = viewModel
            roundFinishDoneButton.setOnClickListener {
                viewModel.finishRound()
                navigate(nextRound())
            }
        }
        effectsService.playFinishTrack()

        return binding.root
    }
}
