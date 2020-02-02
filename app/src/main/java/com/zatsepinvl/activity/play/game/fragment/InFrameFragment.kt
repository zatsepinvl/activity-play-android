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
import com.zatsepinvl.activity.play.core.model.GameAction.*
import com.zatsepinvl.activity.play.databinding.FragmentRoundInBinding
import com.zatsepinvl.activity.play.game.GameViewModel
import com.zatsepinvl.activity.play.game.model.GameStatus
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class InFrameFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: GameViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = FragmentRoundInBinding.inflate(inflater, container, false)
        dataBinding.viewmodel = viewModel
        dataBinding.lifecycleOwner = this

        viewModel.gameState.observe(viewLifecycleOwner, Observer<GameStatus> { gameState ->
            if (gameState == GameStatus.FINISH) {
                findNavController().navigate(R.id.finishRoundFragment)
            }
        })

        val actionImage = dataBinding.roundInActionImage
        viewModel.currentTask.observe(viewLifecycleOwner, Observer { task ->
            when (task.action) {
                SHOW -> R.drawable.theater
                SAY -> R.drawable.karaoke
                DRAW -> R.drawable.art
            }.apply(actionImage::setImageResource)
        })

        return dataBinding.root
    }

}
