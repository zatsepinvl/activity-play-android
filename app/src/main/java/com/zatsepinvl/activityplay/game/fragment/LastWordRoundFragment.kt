package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activityplay.android.fragment.disableBackButton
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.databinding.FragmentRoundLastWordBinding
import com.zatsepinvl.activityplay.game.fragment.LastWordRoundFragmentDirections.Companion.finishRound
import com.zatsepinvl.activityplay.game.viewmodel.GameViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LastWordRoundFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val gameViewModel: GameViewModel by activityViewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        disableBackButton()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        gameViewModel.lastTaskFinishedEvent.observe(viewLifecycleOwner) {
            navigate(finishRound())
        }

        val binding = FragmentRoundLastWordBinding.inflate(inflater, root, false)
        binding.lifecycleOwner = this
        binding.gameViewmodel = gameViewModel
        return binding.root
    }
}
