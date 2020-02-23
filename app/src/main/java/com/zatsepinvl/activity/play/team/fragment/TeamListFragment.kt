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
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.android.dismissDialog
import com.zatsepinvl.activity.play.databinding.FragmentTeamListBinding
import com.zatsepinvl.activity.play.databinding.ViewTeamListItemBinding
import com.zatsepinvl.activity.play.di.ViewModelAwareFragment
import com.zatsepinvl.activity.play.team.model.*
import com.zatsepinvl.activity.play.team.viewmodel.DeleteTeamErrorCode.AT_LEAST_TWO_TEAMS_REQUIRED
import com.zatsepinvl.activity.play.team.viewmodel.TeamSettingsViewModel
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initNavButton()
        initAddNewTeamButton()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == UpdateTeamDialogRequestCode.REQUEST_NEW.code) {
            val newTeamDto = data!!.getNewTeamDto()
            viewModel.addTeam(newTeamDto.name, newTeamDto.colorId)
        } else if (requestCode == UpdateTeamDialogRequestCode.REQUEST_UPDATE.code) {
            val editTeamDto = data!!.getUpdateTeamDto()
            viewModel.updateTeam(editTeamDto.id, editTeamDto.name, editTeamDto.colorId)
        }
    }

    private fun initNavButton() {
        teamListSettingsButton.setOnClickListener {
            findNavController().navigate(TeamListFragmentDirections.gameSettings())
        }
        teamListStartNewGameButton.setOnClickListener {
            findNavController().navigate(TeamListFragmentDirections.startRound())
        }
        teamListBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initAddNewTeamButton() {
        teamListAddTeamButton.setOnClickListener {
            val addTeamDialog = UpdateTeamDialogFragment()
            addTeamDialog.setTargetFragment(this, UpdateTeamDialogRequestCode.REQUEST_NEW.code)
            addTeamDialog.show(requireFragmentManager(), "addTeamDialog")
        }
    }

    private fun observeTeams(teamsContainer: ViewGroup, inflater: LayoutInflater) {
        viewModel.teams.observe(viewLifecycleOwner, Observer { teams ->
            teamsContainer.removeAllViews()
            teams.forEach { team ->
                val teamBinding = ViewTeamListItemBinding.inflate(
                    inflater, teamsContainer, true
                )
                teamBinding.team = team
                initTeamItemButtons(team, teamBinding)
            }
        })
    }

    private fun initTeamItemButtons(team: Team, binding: ViewTeamListItemBinding) {
        binding.teamListItemDeleteTeamButton.setOnClickListener {
            when (viewModel.canDeleteTeam()) {
                AT_LEAST_TWO_TEAMS_REQUIRED -> AlertDialog.Builder(requireContext())
                    .setMessage(R.string.deleteTeamDialogNotEnoughTeams)
                    .setPositiveButton(R.string.ok, dismissDialog)
                    .show()
                else -> AlertDialog.Builder(requireContext())
                    .setMessage(R.string.deleteTeamDialogMessage)
                    .setPositiveButton(R.string.delete) { _, _ -> viewModel.deleteTeam(team.id) }
                    .setNegativeButton(R.string.cancel, dismissDialog)
                    .show()
            }
        }

        binding.teamListItemEditTeam.setOnClickListener {
            val addTeamDialog = UpdateTeamDialogFragment()
            addTeamDialog.arguments = Bundle().putUpdateTeamDto(
                UpdateTeamDto(team.id, team.name, team.colorId)
            )
            addTeamDialog.setTargetFragment(this, UpdateTeamDialogRequestCode.REQUEST_UPDATE.code)
            addTeamDialog.show(requireFragmentManager(), "editTeamDialog")
        }
    }
}