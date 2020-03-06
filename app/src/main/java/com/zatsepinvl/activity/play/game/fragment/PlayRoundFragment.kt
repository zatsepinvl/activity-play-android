package com.zatsepinvl.activity.play.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activity.play.android.fragment.disableBackButton
import com.zatsepinvl.activity.play.core.model.GameAction.*
import com.zatsepinvl.activity.play.databinding.FragmentRoundPlayBinding
import com.zatsepinvl.activity.play.game.viewmodel.PlayRoundViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PlayRoundFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PlayRoundViewModel by activityViewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        disableBackButton()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!viewModel.roundPlaying) {
            viewModel.start()
        }

        val dataBinding = FragmentRoundPlayBinding.inflate(inflater, container, false)
        dataBinding.viewmodel = viewModel
        dataBinding.lifecycleOwner = this

        val hideDrawButton = { dataBinding.gameFrameDrawButton.visibility = View.GONE }
        val showDrawButton = { dataBinding.gameFrameDrawButton.visibility = View.VISIBLE }

        viewModel.currentTask.observe(viewLifecycleOwner, Observer { task ->
            when (task.action) {
                SHOW -> hideDrawButton()
                SAY -> hideDrawButton()
                DRAW -> showDrawButton()
            }
        })

        viewModel.finishRoundEvent.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(PlayRoundFragmentDirections.askLastWord())
        })

        dataBinding.gameFrameFinishButton.setOnClickListener {
            viewModel.stopRound()
        }
        dataBinding.gameFrameDrawButton.setOnClickListener {
            findNavController().navigate(PlayRoundFragmentDirections.canvas())
        }

        return dataBinding.root
    }
}
