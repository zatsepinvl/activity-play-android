package com.zatsepinvl.activity.play.game

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activity.play.BasicFragment
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.databinding.FragmentGameStartBinding
import com.zatsepinvl.activity.play.game.model.GameState
import com.zatsepinvl.activity.play.game.model.GameViewModel

class StartGameFragment : BasicFragment<FragmentGameStartBinding>(
    R.layout.fragment_game_start
) {
    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateBinding(binding: FragmentGameStartBinding) {
        binding.viewmodel = viewModel
    }

    override fun onCreateView() {
        viewModel.gameState.observe(viewLifecycleOwner, Observer<GameState> { gameState ->
            if (gameState == GameState.PLAY) {
                findNavController().navigate(R.id.inGameFragment)
            }
        })
    }
}
