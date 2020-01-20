package com.zatsepinvl.activity.play.settings.fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.SeekBarPreference
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.android.DaggerPreferenceFragmentCompat
import com.zatsepinvl.activity.play.dictionary.DictionaryService
import com.zatsepinvl.activity.play.dictionary.getSupportedLanguageFromTag
import com.zatsepinvl.activity.play.settings.ActivityPlayPreference
import com.zatsepinvl.activity.play.settings.ActivityPlayPreferenceActionKey
import com.zatsepinvl.activity.play.settings.ActivityPlayPreferenceKey.*
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
        findPreference<ListPreference>(DICTIONARY_LANGUAGE.key)?.setOnPreferenceChangeListener { _, newValue ->
            onDictionaryLanguageChanged(newValue as String)
            true
        }
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