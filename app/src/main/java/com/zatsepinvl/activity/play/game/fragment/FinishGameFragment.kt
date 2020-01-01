package com.zatsepinvl.activity.play.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.databinding.FragmentGameFinishBinding
import com.zatsepinvl.activity.play.databinding.LayoutGameFinishTeamScoreItemBinding
import com.zatsepinvl.activity.play.game.FinishGameViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_game_finish.*
import javax.inject.Inject

class FinishGameFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FinishGameViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = FragmentGameFinishBinding.inflate(inflater, container, false)
        dataBinding.lifecycleOwner = this

        val itemRoot = dataBinding.fragmentGameFinishTeamsScoreRoot
        viewModel.getTeamResults().forEach {
            val resultBinding =
                LayoutGameFinishTeamScoreItemBinding.inflate(inflater)
            resultBinding.teamResult = it
            itemRoot.addView(resultBinding.root)
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gameFinishDoneButton.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

    }

}
