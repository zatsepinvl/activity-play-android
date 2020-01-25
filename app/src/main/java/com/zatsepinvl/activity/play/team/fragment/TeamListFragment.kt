package com.zatsepinvl.activity.play.team.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zatsepinvl.activity.play.databinding.FragmentTeamListBinding
import com.zatsepinvl.activity.play.databinding.ViewTeamListItemBinding
import com.zatsepinvl.activity.play.di.ViewModelAwareFragment
import com.zatsepinvl.activity.play.navigation.NavigationFlow.NEW_GAME
import com.zatsepinvl.activity.play.team.DeleteTeamErrorCode.AT_LEAST_TWO_TEAMS_REQUIRED
import com.zatsepinvl.activity.play.team.TeamSettingsViewModel
import kotlinx.android.synthetic.main.fragment_team_list.*

class TeamListFragment :
    ViewModelAwareFragment<TeamSettingsViewModel>(TeamSettingsViewModel::class) {

    private val args: TeamListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        observeTeams(binding.teamList, inflater)
        observeErrorEvents()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initStartNewGameButton()
        initAddNewTeamButton()
        teamListBackButton.setOnClickListener { findNavController().popBackStack() }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initStartNewGameButton() {
        if (args.navigationFlow != NEW_GAME) {
            teamListStartNewGameButton.visibility = View.GONE
        } else {
            teamListStartNewGameButton.setOnClickListener {
                findNavController().navigate(TeamListFragmentDirections.startRound())
            }
        }
    }

    private fun initAddNewTeamButton() {
        teamListAddTeamButton.setOnClickListener {
            val addTeamDialog = AddNewTeamDialogFragment()
            addTeamDialog.setTargetFragment(this, 0)
            addTeamDialog.show(requireFragmentManager(), "addTeamDialog")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val newTeam = data!!.getNewTeam()
        viewModel.addTeam(newTeam.name, newTeam.colorId)
    }

    private fun observeTeams(teamsContainer: ViewGroup, inflater: LayoutInflater) {
        viewModel.teams.observe(viewLifecycleOwner, Observer { teams ->
            teamsContainer.removeAllViews()
            teams.forEach { team ->
                val teamBinding = ViewTeamListItemBinding.inflate(
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
                AT_LEAST_TWO_TEAMS_REQUIRED -> AlertDialog.Builder(requireContext())
                    .setMessage(
                        "You can not delete team as at least two teams are required for game."
                    )
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        })
    }
}