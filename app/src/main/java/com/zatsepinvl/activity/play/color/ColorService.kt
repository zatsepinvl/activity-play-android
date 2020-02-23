package com.zatsepinvl.activity.play.color

import android.content.Context
import androidx.core.content.res.getResourceIdOrThrow
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.android.color
import java.util.*
import javax.inject.Inject

data class Color(
    val id: ColorId,
    val name: String,
    val hexCode: Int
)

enum class ColorId {
    RED,
    PINK,
    PURPLE,
    DEEP_PURPLE,
    INDIGO,
    BLUE,
    LIGHT_BLUE,
    CYAN,
    TEAL,
    GREEN,
    LIGHT_GREEN,
    LIME,
    YELLOW,
    AMBER,
    ORANGE,
    DEEP_ORANGE,
    BROWN,
    GREY,
    BLUE_GREY,
    BLACK
}

class ColorService @Inject constructor(context: Context) {
    private val colors: Map<ColorId, Color>

    init {
        val resourceColors = context.resources.obtainTypedArray(R.array.colors)
        val resourceColorNames = context.resources.obtainTypedArray(R.array.colorNames)
        val resourceColorIds = context.resources.obtainTypedArray(R.array.colorIds)
        val colorIdMap = ColorId.values().map { it.name.toLowerCase(Locale.ROOT) to it }.toMap()
        colors = (0 until resourceColors.length())
            .map {
                val resource = context.color(resourceColors.getResourceIdOrThrow(it))
                val id = colorIdMap[resourceColorIds.getString(it) ?: ""]
                    ?: throw IllegalStateException("Can not find color by index $it")
                val name = resourceColorNames.getString(it) ?: ""
                id to Color(id, name, resource)
            }.toMap()

        resourceColors.recycle()
        resourceColorIds.recycle()
        resourceColorNames.recycle()
    }

    fun getColorByIndex(index: Int): Color {
        require(index > -1 && index < colors.size) {
            "$index is out of bound of colors list"
        }
        return colors.values.toList()[index]
    }

    fun getColorById(id: ColorId): Color {
        return colors[id] ?: throw IllegalArgumentException("Color is not found by id $id")
    }

    fun getAllColors(): List<Color> = colors.values.toList()
}
