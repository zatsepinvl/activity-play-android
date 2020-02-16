package com.zatsepinvl.activity.play.android.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.android.color

class Button(context: Context, attrs: AttributeSet) : MaterialButton(context, attrs) {
    var color: Int = 0
        set(value) {
            val color = ColorStateList.valueOf(value)
            strokeColor = color
            setTextColor(color)
            iconTint = color
            rippleColor = color
            field = value
        }

    init {
        val attributes = context.theme.obtainStyledAttributes(
            attrs, R.styleable.Button, 0, 0
        )
        try {
            color = attributes.getColor(
                R.styleable.Button_color,
                context.color(R.color.colorPrimary)
            )
        } finally {
            attributes.recycle()
        }
    }

}