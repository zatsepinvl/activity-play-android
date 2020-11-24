package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activityplay.android.fragment.disableBackButton
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.databinding.FragmentRoundPlayBinding
import com.zatsepinvl.activityplay.game.fragment.PlayRoundFragmentDirections.Companion.askLastWord
import com.zatsepinvl.activityplay.game.viewmodel.GameEffectsViewModel
import com.zatsepinvl.activityplay.game.viewmodel.GameViewModel
import com.zatsepinvl.activityplay.game.viewmodel.RoundUiViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PlayRoundFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val gameViewModel: GameViewModel by activityViewModels { viewModelFactory }
    private val uiViewModel: RoundUiViewModel by activityViewModels { viewModelFactory }
    private val effectsViewModel: GameEffectsViewModel by activityViewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        disableBackButton()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        effectsViewModel.subscribeOnGameEvents(viewLifecycleOwner, gameViewModel)
        gameViewModel.mainPartFinishedEvent.observe(viewLifecycleOwner) {
            navigate(askLastWord())
        }

        val binding = FragmentRoundPlayBinding.inflate(inflater, root, false)
        binding.gameViewmodel = gameViewModel
        binding.uiViewmodel = uiViewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}
