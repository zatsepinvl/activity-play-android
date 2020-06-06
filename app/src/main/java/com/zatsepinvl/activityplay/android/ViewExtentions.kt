package com.zatsepinvl.activityplay.android

import android.view.View

fun View.onClick(listener: (View) -> Unit) = this.setOnClickListener(listener)