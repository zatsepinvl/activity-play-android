package com.zatsepinvl.activityplay.loading

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.zatsepinvl.activityplay.databinding.DialogLoadingBinding

object LoadingDialog {
    fun build(activity: Activity, loadingData: LoadingData): AlertDialog {
        val binding = DialogLoadingBinding.inflate(activity.layoutInflater)
        binding.data = loadingData
        return AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
    }
}