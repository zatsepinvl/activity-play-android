package com.zatsepinvl.activity.play.settings.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.SeekBarPreference
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.android.DaggerPreferenceFragmentCompat
import com.zatsepinvl.activity.play.dictionary.DictionaryService
import com.zatsepinvl.activity.play.dictionary.getSupportedLanguageFromTag
import com.zatsepinvl.activity.play.settings.ActivityPlayPreference
import com.zatsepinvl.activity.play.settings.model.ActivityPlayPreferenceActionKey
import com.zatsepinvl.activity.play.settings.model.ActivityPlayPreferenceKey.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameSettingsFragment : DaggerPreferenceFragmentCompat() {

    @Inject
    lateinit var dictionaryService: DictionaryService

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        findPreference<SeekBarPreference>(ROUND_TIME_SECONDS.key)?.increment(10)
        findPreference<SeekBarPreference>(MAX_SCORE.key)?.increment(10)
        ActivityPlayPreferenceActionKey.values().toList().forEach {
            findPreference<CheckBoxPreference>(it.key)?.validateAtLeastOneActionEnabled()
        }
        findPreference<ListPreference>(DICTIONARY_LANGUAGE.key)?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                onDictionaryLanguageChanged(newValue as String)
                true
            }
            if (value == "default") {
                value = dictionaryService.getDefaultLanguage().tag
            }
        }
    }

    //Hack to add header to preference screen
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val header = layoutInflater.inflate(R.layout.view_settings_header, null)
        (view as ViewGroup).addView(header, 0,
            FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                setMargins(20, 20, 20, 30)
            }
        )
        header.findViewById<View>(R.id.settingsBackButton)
            .setOnClickListener { findNavController().popBackStack() }

        return view
    }

    private fun onDictionaryLanguageChanged(newLangTag: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            //fuck off, i wanna it, i use it.
            val progress = ProgressDialog(context).apply {
                setTitle(R.string.loading_dictionary_progress_title)
                setCancelable(false)
            }
            progress.show()
            uploadDictionary(newLangTag)
            progress.dismiss()
        }
    }

    private suspend fun uploadDictionary(newLangTag: String) {
        withContext(Dispatchers.IO) {
            dictionaryService.loadDictionary(
                getSupportedLanguageFromTag(newLangTag)
            )
        }
    }

}


private fun CheckBoxPreference.validateAtLeastOneActionEnabled() {
    setOnPreferenceChangeListener { _, newValueRaw ->
        val newValue = newValueRaw as Boolean
        val canChangeValue = ActivityPlayPreference.getEnabledActionPreferenceKeys(context)
            .map { it.key }.any { it != key } || newValue

        if (!canChangeValue) {
            AlertDialog.Builder(context)
                .setMessage("You can not disable this action. At least one enabled action is required.")
                .setPositiveButton("OK") { _, _ -> }
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