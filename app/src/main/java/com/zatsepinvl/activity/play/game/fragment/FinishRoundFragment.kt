package com.zatsepinvl.activity.play.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activity.play.android.fragment.disableBackButton
import com.zatsepinvl.activity.play.databinding.FragmentRoundFinishBinding
import com.zatsepinvl.activity.play.game.viewmodel.FinishRoundViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FinishRoundFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FinishRoundViewModel by activityViewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        disableBackButton()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.start()

        val dataBinding = FragmentRoundFinishBinding.inflate(inflater, container, false)
        dataBinding.viewmodel = viewModel
        dataBinding.lifecycleOwner = this
        dataBinding.roundFinishDoneButton.setOnClickListener {
            viewModel.completeRound()
            findNavController().navigate(FinishRoundFragmentDirections.nextRound())
        }

        return dataBinding.root
    }
}
