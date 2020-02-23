package com.zatsepinvl.activity.play.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activity.play.android.fragment.disableBackButton
import com.zatsepinvl.activity.play.databinding.FragmentRoundLastWordBinding
import com.zatsepinvl.activity.play.game.viewmodel.PlayRoundViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LastWordRoundFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PlayRoundViewModel by activityViewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        disableBackButton()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        val dataBinding = FragmentRoundLastWordBinding.inflate(inflater, root, false)
        dataBinding.viewmodel = viewModel
        dataBinding.lifecycleOwner = this

        dataBinding.roundLastWordNoButton.setOnClickListener {
            findNavController().navigate(LastWordRoundFragmentDirections.finishRound())
        }

        dataBinding.roundLastWordYesButton.setOnClickListener {
            viewModel.completeLastWord()
            findNavController().navigate(LastWordRoundFragmentDirections.finishRound())
        }

        return dataBinding.root
    }
}
