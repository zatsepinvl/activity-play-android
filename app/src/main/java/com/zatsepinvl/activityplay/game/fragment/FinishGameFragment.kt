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
import com.zatsepinvl.activityplay.game.viewmodel.GameFinishViewModel
import com.zatsepinvl.activityplay.game.viewmodel.GameEffectsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FinishGameFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val gameFinishViewModel: GameFinishViewModel by activityViewModels { viewModelFactory }
    private val effectsViewModel: GameEffectsViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        val dataBinding = FragmentGameFinishBinding.inflate(inflater, root, false)
        dataBinding.lifecycleOwner = this
        dataBinding.apply {
            gameFinishDoneButton.setOnClickListener { navigate(backHome()) }
        }

        val teamScoresRootView = dataBinding.fragmentGameFinishTeamsScoreRoot
        gameFinishViewModel.getTeamResults().forEach {
            val resultBinding = ViewGameFinishTeamScoreItemBinding.inflate(
                inflater, teamScoresRootView, true
            )
            resultBinding.data = it
            if (it.winner) {
                dataBinding.winner = it
                (requireActivity() as ColoredView).changeBackgroundColor(it.team.color)
            }
        }

        effectsViewModel.onGameFinished()

        return dataBinding.root
    }
}
