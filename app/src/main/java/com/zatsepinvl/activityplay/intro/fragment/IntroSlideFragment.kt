package com.zatsepinvl.activityplay.intro.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zatsepinvl.activityplay.databinding.FragmentIntroSlideBinding
import com.zatsepinvl.activityplay.intro.model.IntroSlideModel

class IntroSlideFragment(
    private val model: IntroSlideModel
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentIntroSlideBinding.inflate(inflater)
        binding.model = model
        return binding.root
    }
}