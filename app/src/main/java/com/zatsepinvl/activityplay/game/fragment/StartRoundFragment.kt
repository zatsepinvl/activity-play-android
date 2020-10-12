package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
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
import com.zatsepinvl.activityplay.game.viewmodel.MultiplayerGameViewModel
import com.zatsepinvl.activityplay.game.viewmodel.StartRoundViewModel
import com.zatsepinvl.activityplay.navigation.NavigationFlow
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class StartRoundFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var effectsService: EffectsService

    private val args: StartRoundFragmentArgs by navArgs()

    private val gameViewModel: StartRoundViewModel by activityViewModels { viewModelFactory }
    private val multiplayerGameViewModel: MultiplayerGameViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        setupGameViewModel()
        setupMultiplayerGameViewModel()

        val dataBinding = FragmentRoundStartBinding.inflate(inflater, root, false)
        dataBinding.gameViewModel = gameViewModel
        dataBinding.multiplayerGameViewModel = multiplayerGameViewModel
        dataBinding.lifecycleOwner = this

        dataBinding.gameStartRoundStartButton.onClick {
            effectsService.playStartRoundTrack()
            navigate(playRound())
        }
        dataBinding.gameStartRoundExitButton.onClick { navigate(backToMenu()) }
        dataBinding.gameSettingsButton.onClick { navigate(settings()) }
        requireActivity().onBackPressedDispatcher.addCallback(this) { navigate(backToMenu()) }

        createTeamBoardView(inflater, dataBinding.startRoundTeamsBoard)

        return dataBinding.root
    }

    private fun setupMultiplayerGameViewModel() {
        if (args.navigationFlow == NavigationFlow.NEW_MULTIPLAYER_GAME) {
            multiplayerGameViewModel.hostMultiplayerGame()
        }
    }

    private fun setupGameViewModel() {
        when (args.navigationFlow) {
            NavigationFlow.NEW_GAME,
            NavigationFlow.NEW_MULTIPLAYER_GAME -> gameViewModel.startNewGame()
            else -> gameViewModel.continueGame()
        }
        if (gameViewModel.isGameFinished()) {
            navigate(finishGame())
        }
        (activity as ColoredView).changeBackgroundColor(gameViewModel.currentTeam.color)
    }

    private fun createTeamBoardView(inflater: LayoutInflater, teamBoard: ViewGroup) {
        gameViewModel.getTeamsBoardItemData().forEach {
            val teamBoardItem = ViewTeamBoardItemBinding.inflate(inflater, teamBoard, true)
            teamBoardItem.data = it
        }
    }
}