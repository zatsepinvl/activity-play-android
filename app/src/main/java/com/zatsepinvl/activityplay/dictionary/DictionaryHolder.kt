package com.zatsepinvl.activityplay.dictionary

import com.zatsepinvl.activityplay.core.Dictionary
import com.zatsepinvl.activityplay.language.SupportedLanguage


interface DictionaryHolder {
    fun loadDictionary(lang: String): Dictionary
    fun getDictionary(): Dictionary
    fun getDefaultLanguage(): SupportedLanguage
}