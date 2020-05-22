package com.zatsepinvl.activityplay.settings.service

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import androidx.preference.PreferenceManager
import com.zatsepinvl.activityplay.language.SupportedLanguage
import com.zatsepinvl.activityplay.language.getSupportedLanguageFromTag
import com.zatsepinvl.activityplay.settings.model.ActivityPlayPreferenceActionKey
import com.zatsepinvl.activityplay.settings.model.ActivityPlayPreferenceKey.*
import com.zatsepinvl.activityplay.settings.model.ActivityPlayPreferences

object ActivityPlayPreference {

    fun getActivityPlayPreferences(context: Context): ActivityPlayPreferences {
        val currentLanguageTag = getDefaultLanguageTag()
        val defaultLanguage = SupportedLanguage.values()
            .find { it.tag == currentLanguageTag } ?: SupportedLanguage.EN
        return PreferenceManager.getDefaultSharedPreferences(context).run {
            ActivityPlayPreferences(
                dictionaryLanguage = getSupportedLanguageFromTag(
                    getString(DICTIONARY_LANGUAGE.key, defaultLanguage.tag)!!
                ),
                fineForSkipping = getBoolean(FINE_FOR_SKIPPING.key, false),
                roundTimeSeconds = getInt(ROUND_TIME_SECONDS.key, 60),
                maxScore = getInt(MAX_SCORE.key, 20),
                enabledActions = getEnabledActionPreferenceKeys(context).map { it.action }.toSet(),
                soundsEnabled = soundsEnabled(),
                vibrationEnabled = vibrationEnabled()
            )
        }
    }

    fun getSoundsEnabled(context: Context): Boolean {
        return getSharedPreferences(context).soundsEnabled()
    }

    fun getVibrationEnabled(context: Context): Boolean {
        return getSharedPreferences(context).vibrationEnabled()
    }

    fun SharedPreferences.soundsEnabled(): Boolean {
        return getBoolean(SOUND_EFFECTS.key, true)
    }

    fun SharedPreferences.vibrationEnabled(): Boolean {
        return getBoolean(VIBRATION.key, true)
    }

    private fun getDefaultLanguageTag(): String {
        return ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0].language
    }

    fun getSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun getEnabledActionPreferenceKeys(context: Context): Set<ActivityPlayPreferenceActionKey> {
        return PreferenceManager.getDefaultSharedPreferences(context).run {
            ActivityPlayPreferenceActionKey.values().toList()
                .filter { getBoolean(it.key, false) }
                .toSet()
        }
    }
}
