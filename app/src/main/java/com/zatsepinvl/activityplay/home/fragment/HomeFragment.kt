package com.zatsepinvl.activityplay.home.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdRequest.ERROR_CODE_NETWORK_ERROR
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.zatsepinvl.activityplay.BuildConfig
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.android.fragment.disableBackButton
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.android.viewmodel.EventObserver
import com.zatsepinvl.activityplay.color.ColoredView
import com.zatsepinvl.activityplay.databinding.FragmentHomeBinding
import com.zatsepinvl.activityplay.home.fragment.HomeFragmentDirections.Companion.continueGame
import com.zatsepinvl.activityplay.home.fragment.HomeFragmentDirections.Companion.intro
import com.zatsepinvl.activityplay.home.fragment.HomeFragmentDirections.Companion.multiplayerLobby
import com.zatsepinvl.activityplay.home.fragment.HomeFragmentDirections.Companion.newGame
import com.zatsepinvl.activityplay.home.fragment.HomeFragmentDirections.Companion.settings
import com.zatsepinvl.activityplay.home.viewmodel.HomeViewModel
import com.zatsepinvl.activityplay.loading.LoadingData
import com.zatsepinvl.activityplay.loading.LoadingDialog
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

private const val SUPPORT_AD_ID = "ca-app-pub-5369622084298684/1487269811"

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by activityViewModels { viewModelFactory }

    private lateinit var rewardedAd: RewardedAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disableBackButton()
    }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater, root, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        (activity as ColoredView).resetBackgroundColor()
        setupNavigation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeNewGameButton.setOnClickListener { navigate(newGame()) }
        homeContinueButton.setOnClickListener { navigate(continueGame()) }
        homeSettingsButton.setOnClickListener { navigate(settings()) }
        homeTutorialButton.setOnClickListener { navigate(intro()) }
        homeSupportButton.setOnClickListener { loadAdd() }

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

    private fun setupNavigation() {
        viewModel.homePageEvent.observe(this, EventObserver {
            navigate(multiplayerLobby())
        })
    }

    private fun loadAdd() {
        val adId = if (BuildConfig.DEBUG) {
            "ca-app-pub-3940256099942544/5224354917"
        } else {
            SUPPORT_AD_ID
        }
        rewardedAd = RewardedAd(requireActivity(), adId)
        val dialog = LoadingDialog.build(
            requireActivity(), LoadingData(
                title = getString(R.string.ad_thank_you),
                text = getString(R.string.ad_just_a_second)
            )
        )
        dialog.show()
        val adLoadCallback = object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                if (dialog.isShowing) {
                    showAd()
                }
                dialog.dismiss()
            }

            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                dialog.dismiss()
                showAddFailedToLoadDialog(errorCode)
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
    }

    private fun showAddFailedToLoadDialog(errorCode: Int) {
        val message = if (errorCode == ERROR_CODE_NETWORK_ERROR) {
            getString(R.string.ad_error_check_internet)
        } else {
            getString(R.string.ad_error_try_later)
        }
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setIcon(R.drawable.ic_error_outline_amber_24dp)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .show()
    }

    private fun showAd() {
        if (rewardedAd.isLoaded) {
            val activityContext = requireActivity()
            val adCallback = object : RewardedAdCallback() {
                override fun onRewardedAdOpened() {}
                override fun onRewardedAdClosed() {}
                override fun onUserEarnedReward(reward: RewardItem) {}
                override fun onRewardedAdFailedToShow(errorCode: Int) {}
            }
            rewardedAd.show(activityContext, adCallback)
        } else {
            Log.d("activity-play", "The rewarded ad wasn't loaded yet.")
        }
    }
}