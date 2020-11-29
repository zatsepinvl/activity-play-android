package com.zatsepinvl.activityplay.game.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zatsepinvl.activityplay.android.fragment.disableBackButton
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.databinding.FragmentRoundFinishBinding
import com.zatsepinvl.activityplay.game.adapter.FinishTaskListAdapter
import com.zatsepinvl.activityplay.game.fragment.FinishRoundFragmentDirections.Companion.nextRound
import com.zatsepinvl.activityplay.game.viewmodel.GameViewModel
import com.zatsepinvl.activityplay.resource.ResourceService
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FinishRoundFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var resourceService: ResourceService

    private val gameViewModel: GameViewModel by activityViewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        disableBackButton()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        gameViewModel.roundFinishedEvent.observe(viewLifecycleOwner) {
            navigate(nextRound())
        }

        val binding = FragmentRoundFinishBinding.inflate(inflater, root, false)
        binding.lifecycleOwner = this
        binding.apply {
            viewmodel = gameViewModel
            createTaskList(roundFinishTaskListRecyclerView)
        }
        return binding.root
    }

    private fun createTaskList(recyclerView: RecyclerView) {
        val viewAdapter = FinishTaskListAdapter(gameViewModel, resourceService)
        val viewManager = LinearLayoutManager(requireContext())
        recyclerView.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }
    }
}
