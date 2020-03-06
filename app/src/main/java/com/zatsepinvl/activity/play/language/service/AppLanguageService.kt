package com.zatsepinvl.activity.play.language.service

import com.zatsepinvl.activity.play.color.ColorService
import com.zatsepinvl.activity.play.dictionary.DictionaryHolder
import com.zatsepinvl.activity.play.language.SupportedLanguage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLanguageService @Inject constructor(
    private val dictionaryHolder: DictionaryHolder,
    private val colorService: ColorService
) {
    fun updateLanguage(language: SupportedLanguage) {
        dictionaryHolder.loadDictionary(language)
        colorService.loadColors()
    }
}