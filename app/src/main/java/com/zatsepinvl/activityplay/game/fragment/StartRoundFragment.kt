package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.android.onClick
import com.zatsepinvl.activityplay.color.ColoredView
import com.zatsepinvl.activityplay.databinding.FragmentRoundStartBinding
import com.zatsepinvl.activityplay.databinding.ViewTeamBoardItemBinding
import com.zatsepinvl.activityplay.effects.EffectsService
import com.zatsepinvl.activityplay.game.fragment.StartRoundFragmentDirections.Companion.backToMenu
import com.zatsepinvl.activityplay.game.fragment.StartRoundFragmentDirections.Companion.finishGame
import com.zatsepinvl.activityplay.game.fragment.StartRoundFragmentDirections.Companion.playRound
import com.zatsepinvl.activityplay.game.fragment.StartRoundFragmentDirections.Companion.settings
import com.zatsepinvl.activityplay.game.viewmodel.GameEffectsViewModel
import com.zatsepinvl.activityplay.game.viewmodel.GameRoomViewModel
import com.zatsepinvl.activityplay.game.viewmodel.GameViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class StartRoundFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var effectsService: EffectsService

    private val roomViewModel: GameRoomViewModel by activityViewModels { viewModelFactory }
    private val gameViewModel: GameViewModel by activityViewModels { viewModelFactory }
    private val effectsViewModel: GameEffectsViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        setupViewModels()

        (activity as ColoredView).changeBackgroundColor(roomViewModel.currentTeam.value!!.color)

        val binding = FragmentRoundStartBinding.inflate(inflater, root, false)
        binding.gameViewModel = roomViewModel
        binding.lifecycleOwner = this

        binding.gameStartRoundStartButton.onClick {
            gameViewModel.startRound()
            navigate(playRound())
        }
        binding.gameStartRoundExitButton.onClick { navigate(backToMenu()) }
        binding.gameSettingsButton.onClick { navigate(settings()) }

        createTeamBoardView(inflater, binding.startRoundTeamsBoard)

        return binding.root
    }

    private fun setupViewModels() {
        roomViewModel.setupGame()
        if (roomViewModel.isGameFinished()) {
            navigate(finishGame())
            return
        }
        effectsViewModel.subscribeOnGameEvents(viewLifecycleOwner, gameViewModel)
    }

    private fun createTeamBoardView(inflater: LayoutInflater, teamBoard: ViewGroup) {
        roomViewModel.getTeamsBoardItemData().forEach {
            val teamBoardItem = ViewTeamBoardItemBinding.inflate(inflater, teamBoard, true)
            teamBoardItem.data = it
        }
    }
}