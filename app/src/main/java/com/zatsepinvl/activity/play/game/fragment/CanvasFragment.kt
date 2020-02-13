package com.zatsepinvl.activity.play.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activity.play.databinding.FragmentCanvasBinding
import com.zatsepinvl.activity.play.game.viewmodel.PlayRoundViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CanvasFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PlayRoundViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ): View? {
        viewModel.finishRoundEvent.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(CanvasFragmentDirections.finishRound())
        })

        val binding = FragmentCanvasBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.canvasBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.canvasClearButton.setOnClickListener {
            binding.canvasPaintView.clear()
        }

        return binding.root
    }
}