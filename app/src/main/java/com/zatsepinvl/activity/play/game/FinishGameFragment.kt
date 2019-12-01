package com.zatsepinvl.activity.play.game

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activity.play.BasicFragment
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.databinding.FragmentGameFinishBinding
import com.zatsepinvl.activity.play.game.model.GameState
import com.zatsepinvl.activity.play.game.model.GameViewModel

class FinishGameFragment : BasicFragment<FragmentGameFinishBinding>(
    R.layout.fragment_game_finish
) {
    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateBinding(binding: FragmentGameFinishBinding) {
        binding.viewmodel = viewModel
    }

    override fun onCreateView() {
        viewModel.gameState.observe(viewLifecycleOwner, Observer<GameState> { gameState ->
            if (gameState == GameState.START) {
                findNavController().navigate(R.id.startGameFragment)
            }
        })
    }
}
