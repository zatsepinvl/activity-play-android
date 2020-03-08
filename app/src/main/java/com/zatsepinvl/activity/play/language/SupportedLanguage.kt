package com.zatsepinvl.activity.play.language

import com.zatsepinvl.activity.play.language.SupportedLanguage.EN
import com.zatsepinvl.activity.play.language.SupportedLanguage.RU
import java.util.*

enum class SupportedLanguage(val locale: Locale) {
    EN(Locale.ENGLISH),
    RU(Locale("ru", "RU"));

    val tag = this.name.toLowerCase(Locale.ROOT)
}

fun getSupportedLanguageFromTag(tag: String): SupportedLanguage {
    return when (tag) {
        "en" -> EN
        "ru" -> RU
        else -> throw IllegalArgumentException("Unsupported language tag: $tag")
    }
}