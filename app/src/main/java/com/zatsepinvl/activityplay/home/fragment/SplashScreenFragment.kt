package com.zatsepinvl.activityplay.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.home.fragment.SplashScreenFragmentDirections.Companion.home
import com.zatsepinvl.activityplay.home.fragment.SplashScreenFragmentDirections.Companion.intro
import com.zatsepinvl.activityplay.language.service.AppLanguageService
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


const val FIRST_VISIT_SP_NAME = "firstVisit"

class SplashScreenFragment : DaggerFragment() {
    @Inject
    lateinit var languageService: AppLanguageService

    init {
        lifecycleScope.launchWhenStarted {
            val activity = requireActivity()

            withContext(Dispatchers.IO) {
                languageService.resetLanguage(activity)
            }

            val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
            if (preferences.getBoolean(FIRST_VISIT_SP_NAME, true)) {
                preferences.edit().putBoolean(FIRST_VISIT_SP_NAME, false).apply()
                navigate(intro())
            } else {
                navigate(home())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }
}