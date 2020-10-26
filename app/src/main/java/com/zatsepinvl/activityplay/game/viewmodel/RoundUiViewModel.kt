package com.zatsepinvl.activityplay.game.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoundUiViewModel @Inject constructor() : ViewModel() {
    val isWordHidden = MutableLiveData<Boolean>()

    fun toggleWordVisibility() {
        isWordHidden.value = !(isWordHidden.value ?: false)
    }
}
