package com.zatsepinvl.activityplay.settings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.SeekBarPreference
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.android.color
import com.zatsepinvl.activityplay.android.fragment.DaggerPreferenceFragmentCompat
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.databinding.ViewSettingsHeaderBinding
import com.zatsepinvl.activityplay.dictionary.DictionaryHolder
import com.zatsepinvl.activityplay.language.SupportedLanguage
import com.zatsepinvl.activityplay.language.getSupportedLanguageFromTag
import com.zatsepinvl.activityplay.language.service.AppLanguageService
import com.zatsepinvl.activityplay.settings.fragment.GameSettingsFragmentDirections.Companion.refresh
import com.zatsepinvl.activityplay.settings.model.ActivityPlayPreferenceActionKey
import com.zatsepinvl.activityplay.settings.model.ActivityPlayPreferenceKey.*
import com.zatsepinvl.activityplay.settings.service.ActivityPlayPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameSettingsFragment : DaggerPreferenceFragmentCompat() {

    @Inject
    lateinit var dictionaryHolder: DictionaryHolder

    @Inject
    lateinit var languageService: AppLanguageService

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        findPreference<SeekBarPreference>(ROUND_TIME_SECONDS.key)?.increment(10)
        findPreference<SeekBarPreference>(MAX_SCORE.key)?.increment(10)
        ActivityPlayPreferenceActionKey.values().toList().forEach {
            findPreference<CheckBoxPreference>(it.key)?.validateAtLeastOneActionEnabled()
        }
        findPreference<ListPreference>(DICTIONARY_LANGUAGE.key)?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                val language = getSupportedLanguageFromTag(newValue as String)
                setAppLanguage(language)
                true
            }
            if (value == "default") {
                value = dictionaryHolder.getDefaultLanguage().tag
            }
        }
    }

    private fun setAppLanguage(language: SupportedLanguage) {
        val activity = requireActivity()
        activity.lifecycleScope.launch {
            val snackbar = createLoadDictionarySnackbar()
            snackbar.show()
            try {
                withContext(Dispatchers.IO) {
                    languageService.resetLanguage(activity, language)
                }
                navigate(refresh())
            } finally {
                snackbar.dismiss()
            }
        }
    }

    private fun createLoadDictionarySnackbar(): Snackbar {
        return Snackbar.make(requireView(), R.string.loading_dictionary_progress_title, LENGTH_INDEFINITE)
            .apply {
                view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    .setTextColor(context.color(R.color.md_white_1000))
            }
    }

    //Workaround to add header to preference screen
    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View {
        val view = super.onCreateView(inflater, root, state) as ViewGroup
        val dinging = ViewSettingsHeaderBinding.inflate(inflater)
        val header = dinging.root
        view.addView(header, 0,
            FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                setMargins(20, 20, 20, 30)
            }
        )
        dinging.settingsBackButton.setOnClickListener { findNavController().popBackStack() }

        return view
    }

}


private fun CheckBoxPreference.validateAtLeastOneActionEnabled() {
    setOnPreferenceChangeListener { _, newValueRaw ->
        val newValue = newValueRaw as Boolean
        val canChangeValue = ActivityPlayPreference.getEnabledActionPreferenceKeys(context)
            .map { it.key }.any { it != key } || newValue

        if (!canChangeValue) {
            AlertDialog.Builder(context)
                .setMessage(R.string.settings_dialog_one_action_required)
                .setPositiveButton(R.string.ok) { _, _ -> }
                .show()
        }

        canChangeValue
    }
}

private fun SeekBarPreference.increment(by: Int) {
    setOnPreferenceChangeListener { _, newValue ->
        value = (newValue as Int) / by * by
        false
    }
}