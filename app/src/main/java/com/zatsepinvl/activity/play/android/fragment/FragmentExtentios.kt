package com.zatsepinvl.activity.play.android.fragment

import androidx.activity.addCallback
import androidx.fragment.app.Fragment

fun Fragment.disableBackButton() {
    requireActivity().onBackPressedDispatcher.addCallback(this) {
    }
}