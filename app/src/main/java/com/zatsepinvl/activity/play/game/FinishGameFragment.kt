package com.zatsepinvl.activity.play.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.databinding.FragmentGameFrameBinding

class FinishGameFragment : Fragment() {

    private val model: GameViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameFrameBinding>(
            inflater,
            R.layout.fragment_game_frame,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewmodel = model
        return binding.root
    }

}
