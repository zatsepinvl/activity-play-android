package com.zatsepinvl.activity.play.team.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.databinding.FragmentTeamSettingsBinding
import com.zatsepinvl.activity.play.databinding.ViewTeamSettingsItemBinding
import com.zatsepinvl.activity.play.di.ViewModelAwareFragment
import com.zatsepinvl.activity.play.navigation.NavigationFlow.NEW_GAME
import com.zatsepinvl.activity.play.team.DeleteTeamErrorCode.AT_LEAST_TWO_TEAMS_REQUIRED
import com.zatsepinvl.activity.play.team.TeamSettingsViewModel
import kotlinx.android.synthetic.main.fragment_team_settings.*

class TeamSettingsFragment :
    ViewModelAwareFragment<TeamSettingsViewModel>(TeamSettingsViewModel::class) {

    val args: TeamSettingsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamSettingsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        observeTeams(binding.teamSettingsItemRoot, inflater)
        observeErrorEvents()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initStartNewGameButton()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initStartNewGameButton() {
        if (args.navigationFlow != NEW_GAME) {
            teamSettingsStartNewGameButton.visibility = View.GONE
        } else {
            teamSettingsStartNewGameButton.setOnClickListener {
                findNavController().navigate(R.id.startRoundFragment)
            }
        }
    }

    private fun observeTeams(teamsContainer: ViewGroup, inflater: LayoutInflater) {
        viewModel.teams.observe(viewLifecycleOwner, Observer { teams ->
            teamsContainer.removeAllViews()
            teams.forEach { team ->
                val teamBinding = ViewTeamSettingsItemBinding.inflate(
                    inflater, teamsContainer, true
                )
                teamBinding.team = team
                teamBinding.viewmodel = viewModel
            }
        })
    }

    private fun observeErrorEvents() {
        viewModel.deleteTeamErrorEvent.observe(viewLifecycleOwner, Observer {
            when (it!!) {
                AT_LEAST_TWO_TEAMS_REQUIRED -> AlertDialog.Builder(activity!!).setMessage(
                    "You can not delete team as at least two teams are required for game."
                )
                    .setNeutralButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        })
    }
}