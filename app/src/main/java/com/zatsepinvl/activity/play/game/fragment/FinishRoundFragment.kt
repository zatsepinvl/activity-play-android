package com.zatsepinvl.activity.play.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activity.play.android.fragment.disableBackButton
import com.zatsepinvl.activity.play.android.fragment.navigate
import com.zatsepinvl.activity.play.databinding.FragmentRoundFinishBinding
import com.zatsepinvl.activity.play.effects.EffectsService
import com.zatsepinvl.activity.play.game.fragment.FinishRoundFragmentDirections.Companion.nextRound
import com.zatsepinvl.activity.play.game.viewmodel.PlayRoundViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FinishRoundFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var effectsService: EffectsService

    private val viewModel: PlayRoundViewModel by activityViewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        disableBackButton()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = FragmentRoundFinishBinding.inflate(inflater, container, false)
        dataBinding.viewmodel = viewModel
        dataBinding.lifecycleOwner = this
        dataBinding.roundFinishDoneButton.setOnClickListener {
            viewModel.finishRound()
            navigate(nextRound())
        }

        effectsService.playFinishTrack()

        return dataBinding.root
    }
}
