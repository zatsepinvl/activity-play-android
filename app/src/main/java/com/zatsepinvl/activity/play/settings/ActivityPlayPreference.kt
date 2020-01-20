package com.zatsepinvl.activity.play.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.zatsepinvl.activity.play.core.GameAction
import com.zatsepinvl.activity.play.core.GameAction.*
import com.zatsepinvl.activity.play.dictionary.SupportedLanguage
import com.zatsepinvl.activity.play.dictionary.getSupportedLanguageFromTag

enum class ActivityPlayPreferenceKey(
    val key: String
) {
    DICTIONARY_LANGUAGE("dictionaryLanguage"),
    FINE_FOR_SKIPPING("fineForSkipping"),
    ROUND_TIME_SECONDS("roundTimeSeconds"),
    MAX_SCORE("maxScore");
}

enum class ActivityPlayPreferenceActionKey(
    val key: String,
    val action: GameAction
) {
    ACTION_SAY("actionSay", SAY),
    ACTION_DRAW("actionDraw", DRAW),
    ACTION_SHOW("actionShow", SHOW),
}

data class ActivityPlayPreferences(
    val dictionaryLanguage: SupportedLanguage,
    val fineForSkipping: Boolean,
    val roundTimeSeconds: Int,
    val maxScore: Int,
    val enabledActions: Set<GameAction>
)

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
                enabledActions = getEnabledActionPreferenceKeys(context).map { it.action }.toSet()
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
