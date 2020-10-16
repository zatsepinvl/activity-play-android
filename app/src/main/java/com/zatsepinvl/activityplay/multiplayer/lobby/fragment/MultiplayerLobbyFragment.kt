package com.zatsepinvl.activityplay.multiplayer.lobby.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.databinding.FragmentMultiplayerLobbyBinding
import com.zatsepinvl.activityplay.game.viewmodel.MultiplayerGameViewModel
import com.zatsepinvl.activityplay.game.viewmodel.RoomLoadingState
import com.zatsepinvl.activityplay.log.APP_LOG_TAG
import com.zatsepinvl.activityplay.multiplayer.lobby.fragment.MultiplayerLobbyFragmentDirections.Companion.joinMultiplayerGame
import com.zatsepinvl.activityplay.multiplayer.lobby.fragment.MultiplayerLobbyFragmentDirections.Companion.newMultiplayerGame
import com.zatsepinvl.activityplay.multiplayer.lobby.viewmodel.MultiplayerLobbyViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MultiplayerLobbyFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val lobbyViewModel: MultiplayerLobbyViewModel by activityViewModels { viewModelFactory }
    private val multiplayerGameViewModel: MultiplayerGameViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        val binding = FragmentMultiplayerLobbyBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewmodel = lobbyViewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        lobbyViewModel.createNewGameEvent.observe(viewLifecycleOwner, {
            navigate(newMultiplayerGame())
        })

        multiplayerGameViewModel.roomLoadingState.observe(viewLifecycleOwner, {
            if (it == RoomLoadingState.LOADED) {
                navigate(joinMultiplayerGame())
            }
        })

        lobbyViewModel.joinGameEvent.observe(viewLifecycleOwner, {
            Log.d(APP_LOG_TAG, "RoomID: $it.roomId")

            multiplayerGameViewModel.joinMultiplayerGame(it.roomId)
        })
    }
}