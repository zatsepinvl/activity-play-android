package com.zatsepinvl.activity.play.android.fragment

import android.content.DialogInterface
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

val dismissDialog = { d: DialogInterface, _: Any -> d.dismiss() }

fun Fragment.disableBackButton() {
    requireActivity().onBackPressedDispatcher.addCallback(this) {
    }
}

fun Fragment.navigate(direction: NavDirections) {
    findNavController().navigate(direction)
}