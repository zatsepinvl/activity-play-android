package com.zatsepinvl.activity.play.language

import com.zatsepinvl.activity.play.color.ColorService
import com.zatsepinvl.activity.play.dictionary.DictionaryHolder
import com.zatsepinvl.activity.play.language.service.AppLanguageService
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class AppLanguageServiceTest {
    private var dictionaryHolderMock = mock(DictionaryHolder::class.java)
    private var colorServiceMock = mock(ColorService::class.java)

    private val languageService = AppLanguageService(dictionaryHolderMock, colorServiceMock)

    @Test
    fun get_default_language() {
        val actualDefaultLanguage = languageService.getDefaultLanguage()
        assertEquals(actualDefaultLanguage.tag, SupportedLanguage.EN.tag)
    }

    @Test
    @Config(qualifiers = "ru")
    fun get_default_language_ru() {
        val actualDefaultLanguage = languageService.getDefaultLanguage()
        assertEquals(actualDefaultLanguage.tag, SupportedLanguage.RU.tag)
    }

}