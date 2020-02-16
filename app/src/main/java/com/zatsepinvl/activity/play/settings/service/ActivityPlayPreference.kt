package com.zatsepinvl.activity.play.settings.service

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.zatsepinvl.activity.play.dictionary.getSupportedLanguageFromTag
import com.zatsepinvl.activity.play.settings.model.ActivityPlayPreferenceActionKey
import com.zatsepinvl.activity.play.settings.model.ActivityPlayPreferenceKey
import com.zatsepinvl.activity.play.settings.model.ActivityPlayPreferences

object ActivityPlayPreference {

    fun getActivityPlayPreferences(context: Context): ActivityPlayPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context).run {
            ActivityPlayPreferences(
                dictionaryLanguage = getSupportedLanguageFromTag(
                    getString(ActivityPlayPreferenceKey.DICTIONARY_LANGUAGE.key, "en")!!
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
