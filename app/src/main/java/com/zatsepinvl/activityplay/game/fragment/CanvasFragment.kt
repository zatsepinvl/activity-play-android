package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.android.onClick
import com.zatsepinvl.activityplay.databinding.FragmentCanvasBinding
import com.zatsepinvl.activityplay.game.fragment.CanvasFragmentDirections.Companion.askLastWord
import com.zatsepinvl.activityplay.game.viewmodel.GameViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CanvasFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val gameViewModel: GameViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        val binding = FragmentCanvasBinding.inflate(inflater, root, false)

        gameViewModel.mainPartFinishedEvent.observe(viewLifecycleOwner) {
            navigate(askLastWord())
        }

        binding.lifecycleOwner = this
        binding.apply {
            gameViewmodel = gameViewModel
            canvasBackButton.onClick { findNavController().popBackStack() }
            canvasClearButton.onClick { canvasPaintView.clear() }
        }
        return binding.root
    }
}