package com.zatsepinvl.activity.play.home.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.color.ColoredView
import com.zatsepinvl.activity.play.databinding.FragmentHomeBinding
import com.zatsepinvl.activity.play.home.viewmodel.HomeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        (activity as ColoredView).resetBackgroundColor()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeNewGameButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.newGame())
        }

        homeContinueButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.continueGame())
        }

        homeSettingsButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.settings())
        }

        homeTutorialButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.intro())
        }

        viewOnGithub.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_link)))
            startActivity(intent)
        }

        contactMe.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse(getString(R.string.email_intent_data))
            intent.data = data
            startActivity(intent)
        }
    }
}