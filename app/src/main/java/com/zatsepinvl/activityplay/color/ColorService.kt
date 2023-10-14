package com.zatsepinvl.activityplay.color

import android.content.Context
import androidx.core.content.res.getResourceIdOrThrow
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.android.color
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

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

@Singleton
class ColorService @Inject constructor(private val context: Context) {
    private var colors: Map<ColorId, Color> = mapOf()
        get() {
            require(field.isNotEmpty()) { "Load colors first" }
            return field
        }

    fun loadColors() {
        val resourceColors = context.resources.obtainTypedArray(R.array.colors)
        val resourceColorNames = context.resources.obtainTypedArray(R.array.color_names)
        val resourceColorIds = context.resources.obtainTypedArray(R.array.colorIds)
        val colorIdMap = ColorId.values().associateBy { it.name.lowercase(Locale.ROOT) }
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
