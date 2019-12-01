package com.zatsepinvl.activity.play.game

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activity.play.BasicFragment
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.databinding.FragmentGameInBinding
import com.zatsepinvl.activity.play.game.model.GameState
import com.zatsepinvl.activity.play.game.model.GameViewModel

class InGameFragment : BasicFragment<FragmentGameInBinding>(
    R.layout.fragment_game_in
) {
    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateBinding(binding: FragmentGameInBinding) {
        binding.viewmodel = viewModel
    }

    override fun onCreateView() {
        viewModel.gameState.observe(viewLifecycleOwner, Observer<GameState> { gameState ->
            if (gameState == GameState.FINISH) {
                findNavController().navigate(R.id.finishGameFragment)
            }
        })
    }
}
