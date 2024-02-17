package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ncorti.slidetoact.SlideToActView
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

    private val viewModel: StartRoundViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View {
        initViewModel()

        val dataBinding = FragmentRoundStartBinding.inflate(inflater, root, false)
        dataBinding.viewmodel = viewModel
        dataBinding.lifecycleOwner = this

        dataBinding.gameStartRoundStartSlider.outerColor = viewModel.currentTeam.color
        dataBinding.gameStartRoundStartSlider.iconColor = viewModel.currentTeam.color
        dataBinding.gameStartRoundStartSlider.onSlideCompleteListener =
            object : SlideToActView.OnSlideCompleteListener {
                override fun onSlideComplete(view: SlideToActView) {
                    effectsService.playStartRoundTrack()
                    navigate(playRound())
                }
            }
        dataBinding.gameStartRoundExitButton.onClick { navigate(backToMenu()) }
        dataBinding.gameSettingsButton.onClick { navigate(settings()) }
        requireActivity().onBackPressedDispatcher.addCallback(this) { navigate(backToMenu()) }

        createTeamBoardView(inflater, dataBinding.startRoundTeamsBoard)

        return dataBinding.root
    }


    private fun initViewModel() {
        if (args.navigationFlow == NavigationFlow.NEW_GAME) {
            viewModel.startNewGame()
        } else {
            viewModel.continueGame()
        }
        if (viewModel.isGameFinished()) {
            navigate(finishGame())
        }
        (activity as ColoredView).changeBackgroundColor(viewModel.currentTeam.color)
    }

    private fun createTeamBoardView(inflater: LayoutInflater, teamBoard: ViewGroup) {
        viewModel.getTeamsBoardItemData().forEach {
            val teamBoardItem = ViewTeamBoardItemBinding.inflate(inflater, teamBoard, true)
            teamBoardItem.data = it
        }
    }
}