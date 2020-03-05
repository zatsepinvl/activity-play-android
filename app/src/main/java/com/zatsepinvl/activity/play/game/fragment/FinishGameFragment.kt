package com.zatsepinvl.activity.play.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.color.ColoredView
import com.zatsepinvl.activity.play.databinding.FragmentGameFinishBinding
import com.zatsepinvl.activity.play.databinding.ViewGameFinishTeamScoreItemBinding
import com.zatsepinvl.activity.play.effects.EffectsService
import com.zatsepinvl.activity.play.game.viewmodel.FinishGameViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FinishGameFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var effectsService: EffectsService

    private val viewModel: FinishGameViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = FragmentGameFinishBinding.inflate(inflater, container, false)
        dataBinding.lifecycleOwner = this
        dataBinding.gameFinishDoneButton.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        val teamScoresRootView = dataBinding.fragmentGameFinishTeamsScoreRoot
        viewModel.getTeamResults().forEach {
            val resultBinding = ViewGameFinishTeamScoreItemBinding.inflate(
                inflater, teamScoresRootView, true
            )
            resultBinding.data = it
            if (it.winner) {
                dataBinding.winner = it
                (requireActivity() as ColoredView).changeBackgroundColor(it.team.color)
            }
        }

        effectsService.playGameOverTrack()

        return dataBinding.root
    }
}
