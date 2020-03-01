package com.zatsepinvl.activity.play.settings.service

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import androidx.preference.PreferenceManager
import com.zatsepinvl.activity.play.dictionary.SupportedLanguage
import com.zatsepinvl.activity.play.dictionary.getSupportedLanguageFromTag
import com.zatsepinvl.activity.play.settings.model.ActivityPlayPreferenceActionKey
import com.zatsepinvl.activity.play.settings.model.ActivityPlayPreferenceKey
import com.zatsepinvl.activity.play.settings.model.ActivityPlayPreferences

object ActivityPlayPreference {

    fun getActivityPlayPreferences(context: Context): ActivityPlayPreferences {
        val currentLanguageTag = getDefaultLanguageTag()
        val defaultLanguage = SupportedLanguage.values()
            .find { it.tag == currentLanguageTag } ?: SupportedLanguage.EN
        return PreferenceManager.getDefaultSharedPreferences(context).run {
            ActivityPlayPreferences(
                dictionaryLanguage = getSupportedLanguageFromTag(
                    getString(
                        ActivityPlayPreferenceKey.DICTIONARY_LANGUAGE.key,
                        defaultLanguage.tag
                    )!!
                ),
                fineForSkipping = getBoolean(
                    ActivityPlayPreferenceKey.FINE_FOR_SKIPPING.key,
                    false
                ),
                roundTimeSeconds = getInt(ActivityPlayPreferenceKey.ROUND_TIME_SECONDS.key, 60),
                maxScore = getInt(ActivityPlayPreferenceKey.MAX_SCORE.key, 20),
                enabledActions = getEnabledActionPreferenceKeys(
                    context
                ).map { it.action }.toSet()
            )
        }
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
