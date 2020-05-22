package com.zatsepinvl.activityplay.di

import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class ViewModelAwareFragment<T : ViewModel>(
    viewModelClass: KClass<T>
) : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel: T by createViewModelLazy(
        viewModelClass,
        { requireActivity().viewModelStore }) { viewModelFactory }
}