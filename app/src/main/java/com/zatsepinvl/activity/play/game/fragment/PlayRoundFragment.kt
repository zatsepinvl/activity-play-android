package com.zatsepinvl.activity.play.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.android.disableBackButton
import com.zatsepinvl.activity.play.core.model.GameAction.*
import com.zatsepinvl.activity.play.databinding.FragmentRoundPlayBinding
import com.zatsepinvl.activity.play.game.viewmodel.PlayRoundViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PlayRoundFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PlayRoundViewModel by activityViewModels { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        disableBackButton()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = FragmentRoundPlayBinding.inflate(inflater, container, false)
        viewModel.start()
        dataBinding.viewmodel = viewModel
        dataBinding.lifecycleOwner = this

        viewModel.finishRoundEvent.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(PlayRoundFragmentDirections.finishRound())
        })

        val actionImage = dataBinding.roundInActionImage
        viewModel.currentTask.observe(viewLifecycleOwner, Observer { task ->
            when (task.action) {
                SHOW -> R.drawable.theater
                SAY -> R.drawable.karaoke
                DRAW -> R.drawable.art
            }.apply(actionImage::setImageResource)
        })

        return dataBinding.root
    }
}
