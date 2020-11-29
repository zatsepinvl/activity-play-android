package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.color.ColoredView
import com.zatsepinvl.activityplay.databinding.FragmentGameFinishBinding
import com.zatsepinvl.activityplay.databinding.ViewGameFinishTeamScoreItemBinding
import com.zatsepinvl.activityplay.game.fragment.FinishGameFragmentDirections.Companion.backHome
import com.zatsepinvl.activityplay.game.viewmodel.GameViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FinishGameFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val gameViewModel: GameViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        gameViewModel.exitEvent.observe(viewLifecycleOwner) { navigate(backHome()) }

        val binding = FragmentGameFinishBinding.inflate(inflater, root, false)
        binding.lifecycleOwner = this
        binding.gameViewModel = gameViewModel

        setupTeamsList(binding, inflater)

        return binding.root
    }

    private fun setupTeamsList(binding: FragmentGameFinishBinding, inflater: LayoutInflater) {
        val teamResults = gameViewModel.getGameBoardSortedByScore()
        for (i in teamResults.indices) {
            val teamResult = teamResults[i]
            val resultBinding = ViewGameFinishTeamScoreItemBinding.inflate(
                inflater, binding.fragmentGameFinishTeamsScoreRoot, true
            )
            resultBinding.teamPosition = i + 1
            resultBinding.data = teamResult
            if (teamResult.winner) {
                binding.winner = teamResult
                (requireActivity() as ColoredView).changeBackgroundColor(teamResult.team.color)
            }
        }
    }
}
