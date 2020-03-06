package com.zatsepinvl.activity.play.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.color.ColoredView
import com.zatsepinvl.activity.play.databinding.FragmentRoundStartBinding
import com.zatsepinvl.activity.play.databinding.ViewTeamBoardItemBinding
import com.zatsepinvl.activity.play.game.service.GameActionService
import com.zatsepinvl.activity.play.game.viewmodel.StartRoundViewModel
import com.zatsepinvl.activity.play.navigation.NavigationFlow
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class StartRoundFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var gameActionService: GameActionService

    private val args: StartRoundFragmentArgs by navArgs()

    private val viewModel: StartRoundViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        startViewModel()

        val dataBinding = FragmentRoundStartBinding.inflate(inflater, container, false)
        dataBinding.viewmodel = viewModel
        dataBinding.lifecycleOwner = this
        dataBinding.gameStartRoundExitButton.setOnClickListener {
            findNavController().navigate(StartRoundFragmentDirections.backToMenu())
        }
        dataBinding.gameStartRoundStartButton.setOnClickListener {
            findNavController().navigate(StartRoundFragmentDirections.playRound())
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(StartRoundFragmentDirections.backToMenu())
        }

        createTeamBoardView(inflater, dataBinding.startRoundTeamsBoard)

        return dataBinding.root
    }


    private fun startViewModel() {
        if (args.navigationFlow == NavigationFlow.NEW_GAME) {
            viewModel.startNewGame()
        } else {
            viewModel.start()
        }
        (activity as ColoredView).changeBackgroundColor(viewModel.currentTeam.color)
        if (viewModel.isGameFinished()) {
            findNavController().navigate(R.id.finishGameFragment)
        }
    }

    private fun createTeamBoardView(inflater: LayoutInflater, teamBoard: ViewGroup) {
        viewModel.getTeamsBoardItemData().forEach {
            val teamBoardItem = ViewTeamBoardItemBinding.inflate(inflater, teamBoard, true)
            teamBoardItem.data = it
        }
    }
}