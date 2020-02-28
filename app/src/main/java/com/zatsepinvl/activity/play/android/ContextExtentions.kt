package com.zatsepinvl.activity.play.android

import android.content.Context
import androidx.core.content.ContextCompat

fun Context.color(resource: Int): Int {
    return ContextCompat.getColor(this, resource)
}

