package com.zatsepinvl.activity.play.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.databinding.FragmentRoundStartBinding
import com.zatsepinvl.activity.play.game.GameStatus
import com.zatsepinvl.activity.play.game.GameViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class StartRoundFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: GameViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.reloadGame()

        val dataBinding = FragmentRoundStartBinding.inflate(inflater, container, false)
        dataBinding.viewmodel = viewModel
        dataBinding.lifecycleOwner = this

        if (viewModel.isGameFinished()) {
            findNavController().navigate(R.id.finishGameFragment)
        }

        viewModel.gameState.observe(viewLifecycleOwner, Observer<GameStatus> { gameState ->
            if (gameState == GameStatus.PLAY) {
                findNavController().navigate(R.id.inRoundFragment)
            }
        })
        return dataBinding.root
    }
}
