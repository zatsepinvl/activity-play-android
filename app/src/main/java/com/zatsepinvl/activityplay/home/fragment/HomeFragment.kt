package com.zatsepinvl.activityplay.home.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.android.fragment.disableBackButton
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.color.ColoredView
import com.zatsepinvl.activityplay.databinding.FragmentHomeBinding
import com.zatsepinvl.activityplay.home.fragment.HomeFragmentDirections.Companion.continueGame
import com.zatsepinvl.activityplay.home.fragment.HomeFragmentDirections.Companion.intro
import com.zatsepinvl.activityplay.home.fragment.HomeFragmentDirections.Companion.newGame
import com.zatsepinvl.activityplay.home.fragment.HomeFragmentDirections.Companion.settings
import com.zatsepinvl.activityplay.home.viewmodel.HomeViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by activityViewModels { viewModelFactory }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disableBackButton()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        (activity as ColoredView).resetBackgroundColor()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.homeNewGameButton.setOnClickListener { navigate(newGame()) }
        binding.homeContinueButton.setOnClickListener { navigate(continueGame()) }
        binding.homeSettingsButton.setOnClickListener { navigate(settings()) }
        binding.homeTutorialButton.setOnClickListener { navigate(intro()) }

        binding.viewOnGithub.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_link)))
            startActivity(intent)
        }

        binding.contactMe.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse(getString(R.string.email_intent_data))
            intent.data = data
            startActivity(intent)
        }
    }
}