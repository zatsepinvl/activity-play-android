package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.color.ColoredView
import com.zatsepinvl.activityplay.databinding.FragmentRoundStartBinding
import com.zatsepinvl.activityplay.databinding.ViewTeamBoardItemBinding
import com.zatsepinvl.activityplay.effects.EffectsService
import com.zatsepinvl.activityplay.game.fragment.StartRoundFragmentDirections.Companion.backToMenu
import com.zatsepinvl.activityplay.game.fragment.StartRoundFragmentDirections.Companion.finishGame
import com.zatsepinvl.activityplay.game.fragment.StartRoundFragmentDirections.Companion.playRound
import com.zatsepinvl.activityplay.game.viewmodel.GameViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class StartRoundFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var effectsService: EffectsService

    private val gameViewModel: GameViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        setupViewModel()

        (activity as ColoredView).changeBackgroundColor(gameViewModel.currentTeam.value!!.color)

        val binding = FragmentRoundStartBinding.inflate(inflater, root, false)
        binding.gameViewModel = gameViewModel
        binding.lifecycleOwner = this

        createTeamBoardView(inflater, binding.startRoundTeamsBoard)

        return binding.root
    }

    private fun setupViewModel() {
        gameViewModel.setupGame()
        if (gameViewModel.isGameFinished()) {
            navigate(finishGame())
            return
        }
        gameViewModel.exitEvent.observe(viewLifecycleOwner) { navigate(backToMenu()) }
        gameViewModel.roundStartedEvent.observe(viewLifecycleOwner) { navigate(playRound()) }
    }

    private fun createTeamBoardView(inflater: LayoutInflater, teamBoard: ViewGroup) {
        gameViewModel.getGameBoard().forEach {
            val teamBoardItem = ViewTeamBoardItemBinding.inflate(inflater, teamBoard, true)
            teamBoardItem.data = it
        }
    }
}