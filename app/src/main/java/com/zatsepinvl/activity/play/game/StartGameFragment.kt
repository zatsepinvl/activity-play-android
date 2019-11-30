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
import com.zatsepinvl.activity.play.databinding.FragmentGameStartBinding

class StartGameFragment : Fragment() {

    private val model: GameViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameStartBinding>(
            inflater,
            R.layout.fragment_game_start,
            container,
            false
        )
        //binding.lifecycleOwner = this
        binding.viewmodel = model
        return binding.root
    }

}
