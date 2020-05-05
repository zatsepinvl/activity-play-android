package com.zatsepinvl.activity.play.color

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ColorServiceTest {

    val context = ApplicationProvider.getApplicationContext<Context>()
    var colorService = ColorService(context).apply { loadColors() }

    @Test
    fun get_all_colors() {
        val actualColorIds = colorService.getAllColors()
            .map { color -> color.id }
            .toTypedArray()

        assertArrayEquals(ColorId.values(), actualColorIds)
    }

    @Test
    fun get_color_by_index() {
        val color = colorService.getColorByIndex(0)
        assertNotNull(color)
    }

    @Test
    fun get_color_by_id() {
        val expectedColorId = ColorId.AMBER
        val actualColor = colorService.getColorById(expectedColorId)
        assertSame(expectedColorId, actualColor.id)
    }


}