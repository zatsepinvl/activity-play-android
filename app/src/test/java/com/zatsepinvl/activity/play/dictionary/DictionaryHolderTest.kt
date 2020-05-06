package com.zatsepinvl.activity.play.dictionary

import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.zatsepinvl.activity.play.language.SupportedLanguage
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DictionaryHolderTest {

    private val context = getApplicationContext<Context>()
    private val dictionaryHolder = DictionaryHolderImpl(context)

    @Test
    fun load_dictionary() {
        val dictionary = dictionaryHolder.loadDictionary(SupportedLanguage.EN)
        assertEquals(dictionary.language, SupportedLanguage.EN.tag)
    }

    @Test
    fun get_dictionary() {
        dictionaryHolder.loadDictionary(SupportedLanguage.EN)
        val dictionary = dictionaryHolder.getDictionary()
        assertEquals(dictionary.language, SupportedLanguage.EN.tag)
    }
}