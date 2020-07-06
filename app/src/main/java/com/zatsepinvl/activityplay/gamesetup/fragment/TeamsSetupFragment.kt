package com.zatsepinvl.activityplay.gamesetup.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.android.fragment.dismissDialog
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.databinding.FragmentTeamsSetupBinding
import com.zatsepinvl.activityplay.databinding.ViewTeamListItemBinding
import com.zatsepinvl.activityplay.di.ViewModelAwareFragment
import com.zatsepinvl.activityplay.gamesetup.fragment.TeamsSetupFragmentDirections.Companion.gameSettings
import com.zatsepinvl.activityplay.gamesetup.fragment.TeamsSetupFragmentDirections.Companion.startRound
import com.zatsepinvl.activityplay.gamesetup.model.UpdateTeamDto
import com.zatsepinvl.activityplay.gamesetup.model.getNewTeamDto
import com.zatsepinvl.activityplay.gamesetup.model.getUpdateTeamDto
import com.zatsepinvl.activityplay.gamesetup.model.putUpdateTeamDto
import com.zatsepinvl.activityplay.gamesetup.viewmodel.DeleteTeamErrorCode.AT_LEAST_TWO_TEAMS_REQUIRED
import com.zatsepinvl.activityplay.gamesetup.viewmodel.TeamsSetupViewModel
import com.zatsepinvl.activityplay.team.model.Team
import kotlinx.android.synthetic.main.fragment_teams_setup.*

class TeamsSetupFragment :
    ViewModelAwareFragment<TeamsSetupViewModel>(TeamsSetupViewModel::class) {

    private val args: TeamsSetupFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamsSetupBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        observeTeams(binding.teamList, inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupNavigation()
        setupAddNewTeamButton()
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

    private fun setupNavigation() {
        teamListSettingsButton.setOnClickListener { navigate(gameSettings()) }
        teamListStartNewGameButton.setOnClickListener { navigate(startRound(args.navigationFlow)) }
        teamListBackButton.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setupAddNewTeamButton() {
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
                    .setMessage(R.string.delete_team_dialog_not_enough_teams)
                    .setPositiveButton(
                        R.string.ok,
                        dismissDialog
                    )
                    .show()
                else -> AlertDialog.Builder(requireContext())
                    .setMessage(R.string.delete_team_dialog_message)
                    .setPositiveButton(R.string.delete) { _, _ -> viewModel.deleteTeam(team.id) }
                    .setNegativeButton(
                        R.string.cancel,
                        dismissDialog
                    )
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