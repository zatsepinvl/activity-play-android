package com.zatsepinvl.activityplay.language.service

import android.app.Activity
import android.app.Application
import android.content.Context
import com.yariksoffice.lingver.Lingver
import com.zatsepinvl.activityplay.color.ColorService
import com.zatsepinvl.activityplay.dictionary.DictionaryHolder
import com.zatsepinvl.activityplay.settings.service.ActivityPlayPreference
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLanguageService @Inject constructor(
    private val dictionaryHolder: DictionaryHolder,
    private val colorService: ColorService
) {
    fun init(application: Application) {
        val language = getCurrentDictionaryLang(application)
        Lingver.init(application, language)
    }

    fun resetLanguage(activity: Activity, language: String? = null) {
        val lang = language ?: getCurrentDictionaryLang(activity)
        val locale = getLocale(lang)
        Lingver.getInstance().setLocale(activity, locale)
        resetServiceLanguages(lang)
    }

    private fun resetServiceLanguages(lang: String) {
        dictionaryHolder.loadDictionary(lang)
        colorService.loadColors()
    }

    private fun getCurrentDictionaryLang(context: Context): String {
        return ActivityPlayPreference
            .getActivityPlayPreferences(context)
            .dictionaryLanguage
    }

    private fun getLocale(lang: String): Locale {
        return when (lang) {
            "en" -> Locale.ENGLISH
            "ru" -> Locale(lang, "RU")
            else -> throw IllegalArgumentException("Unsupported language $lang")
        }
    }
}