package com.zatsepinvl.activityplay.android

import android.content.Context
import androidx.core.content.ContextCompat

fun Context.color(resource: Int): Int {
    return ContextCompat.getColor(this, resource)
}

