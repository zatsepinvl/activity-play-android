package com.zatsepinvl.activity.play.android.fragment

import android.content.DialogInterface
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

val dismissDialog = { d: DialogInterface, _: Any -> d.dismiss() }

fun Fragment.disableBackButton() {
    requireActivity().onBackPressedDispatcher.addCallback(this) {
    }
}