package com.zatsepinvl.activity.play.dictionary

import com.zatsepinvl.activity.play.dictionary.SupportedLanguage.EN
import com.zatsepinvl.activity.play.dictionary.SupportedLanguage.RU
import java.util.*

enum class SupportedLanguage {
    EN,
    RU;

    val tag = this.name.toLowerCase(Locale.ROOT)
}

fun getSupportedLanguageFromTag(tag: String): SupportedLanguage {
    return when (tag) {
        "en" -> EN
        "ru" -> RU
        else -> throw IllegalArgumentException("Unsupported language tag: $tag")
    }
}