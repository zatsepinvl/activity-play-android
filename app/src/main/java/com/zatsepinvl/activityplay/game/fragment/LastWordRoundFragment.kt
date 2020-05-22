package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zatsepinvl.activityplay.android.fragment.disableBackButton
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.databinding.FragmentRoundLastWordBinding
import com.zatsepinvl.activityplay.game.viewmodel.PlayRoundViewModel
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
        viewModel.startLastWordTimer()

        dataBinding.roundLastWordYesButton.setOnClickListener {
            viewModel.completeLastWord()
            finishRound()
        }
        dataBinding.roundLastWordNoButton.setOnClickListener { finishRound() }
        viewModel.lastWordTimerFinishedEvent.observe(viewLifecycleOwner, Observer { finishRound() })

        return dataBinding.root
    }

    private fun finishRound() {
        navigate(LastWordRoundFragmentDirections.finishRound())
    }
}
