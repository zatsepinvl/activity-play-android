package com.zatsepinvl.activityplay.multiplayer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activityplay.databinding.FragmentMultiplayerLobbyBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MultiplayerLobbyFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        val dataBinding = FragmentMultiplayerLobbyBinding.inflate(inflater)
        return dataBinding.root
    }
}