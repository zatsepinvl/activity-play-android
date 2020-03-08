package com.zatsepinvl.activity.play.language.service

import android.app.Activity
import android.app.Application
import android.content.Context
import com.yariksoffice.lingver.Lingver
import com.zatsepinvl.activity.play.color.ColorService
import com.zatsepinvl.activity.play.dictionary.DictionaryHolder
import com.zatsepinvl.activity.play.language.SupportedLanguage
import com.zatsepinvl.activity.play.settings.service.ActivityPlayPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLanguageService @Inject constructor(
    private val dictionaryHolder: DictionaryHolder,
    private val colorService: ColorService
) {
    fun init(application: Application) {
        val language = getCurrentLanguage(application)
        Lingver.init(application, language.locale)
    }

    fun resetLanguage(activity: Activity, language: SupportedLanguage? = null) {
        val lang = language ?: getCurrentLanguage(activity)
        Lingver.getInstance().setLocale(activity, lang.locale)
        resetServiceLanguages(lang)
    }

    private fun resetServiceLanguages(language: SupportedLanguage) {
        dictionaryHolder.loadDictionary(language)
        colorService.loadColors()
    }

    private fun getCurrentLanguage(context: Context): SupportedLanguage {
        return ActivityPlayPreference
            .getActivityPlayPreferences(context)
            .dictionaryLanguage
    }
}