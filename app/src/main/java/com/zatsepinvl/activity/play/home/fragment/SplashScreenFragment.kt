package com.zatsepinvl.activity.play.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.yariksoffice.lingver.Lingver
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.dictionary.DictionaryService
import com.zatsepinvl.activity.play.settings.service.ActivityPlayPreference
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


const val FIRST_VISIT_SP_NAME = "firstVisit"

class SplashScreenFragment : DaggerFragment() {
    @Inject
    lateinit var dictionaryService: DictionaryService

    init {
        lifecycleScope.launchWhenStarted {
            val context = requireContext()
            val language = ActivityPlayPreference
                .getActivityPlayPreferences(context)
                .dictionaryLanguage
            Lingver.getInstance().setLocale(context, Locale.forLanguageTag(language.tag))
            withContext(Dispatchers.IO) {
                dictionaryService.loadDictionary(language)
            }

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            if (preferences.getBoolean(FIRST_VISIT_SP_NAME, true)) {
                preferences.edit().putBoolean(FIRST_VISIT_SP_NAME, false).apply()
                findNavController().navigate(SplashScreenFragmentDirections.intro())
            } else {
                findNavController().navigate(SplashScreenFragmentDirections.home())
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