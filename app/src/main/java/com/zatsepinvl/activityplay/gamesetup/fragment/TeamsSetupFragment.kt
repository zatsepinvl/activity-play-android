package com.zatsepinvl.activityplay.gamesetup.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.android.fragment.dismissDialog
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.databinding.FragmentTeamsSetupBinding
import com.zatsepinvl.activityplay.databinding.ViewTeamListItemBinding
import com.zatsepinvl.activityplay.gameroom.model.GameRoomMode.SINGLEPLAYER
import com.zatsepinvl.activityplay.gamesetup.fragment.TeamsSetupFragmentDirections.Companion.gameSettings
import com.zatsepinvl.activityplay.gamesetup.fragment.TeamsSetupFragmentDirections.Companion.startRound
import com.zatsepinvl.activityplay.gamesetup.model.*
import com.zatsepinvl.activityplay.gamesetup.model.GameSetupMode.CONTINUE
import com.zatsepinvl.activityplay.gamesetup.model.GameSetupMode.START_NEW
import com.zatsepinvl.activityplay.gamesetup.viewmodel.DeleteTeamErrorCode.AT_LEAST_TWO_TEAMS_REQUIRED
import com.zatsepinvl.activityplay.gamesetup.viewmodel.GameSetupViewModel
import com.zatsepinvl.activityplay.gamesetup.viewmodel.TeamsSetupViewModel
import com.zatsepinvl.activityplay.navigation.GameSetupNavigationFlow.CONTINUE_SINGLEPLAYER_GAME
import com.zatsepinvl.activityplay.navigation.GameSetupNavigationFlow.NEW_SINGLEPLAYER_GAME
import com.zatsepinvl.activityplay.team.model.Team
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TeamsSetupFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val teamsSetupViewModel: TeamsSetupViewModel by activityViewModels { viewModelFactory }
    private val gameSetupViewModel: GameSetupViewModel by activityViewModels { viewModelFactory }

    private val args: TeamsSetupFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, state: Bundle?): View? {
        val binding = FragmentTeamsSetupBinding.inflate(inflater, root, false)
        setupGameSetupViewModel()

        binding.lifecycleOwner = this
        binding.gameSetupViewModel = gameSetupViewModel

        setupNavigation(binding)
        setupView(binding)

        observeTeams(binding.teamList, inflater)

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == UpdateTeamDialogRequestCode.REQUEST_NEW.code) {
            val newTeamDto = data!!.getNewTeamDto()
            teamsSetupViewModel.addTeam(newTeamDto.name, newTeamDto.colorId)
        } else if (requestCode == UpdateTeamDialogRequestCode.REQUEST_UPDATE.code) {
            val editTeamDto = data!!.getUpdateTeamDto()
            teamsSetupViewModel.updateTeam(editTeamDto.id, editTeamDto.name, editTeamDto.colorId)
        }
    }

    private fun setupGameSetupViewModel() {
        when (args.gameSetupNavigationFlow) {
            NEW_SINGLEPLAYER_GAME -> gameSetupViewModel.startGameSetup(START_NEW, SINGLEPLAYER)
            CONTINUE_SINGLEPLAYER_GAME -> gameSetupViewModel.startGameSetup(CONTINUE, SINGLEPLAYER)
            else -> throw NotImplementedError("Multiplayer is not supported yet.")
        }
        gameSetupViewModel.gameSetupFinishedEvent.observe(viewLifecycleOwner) {
            navigate(startRound(args.gameSetupNavigationFlow))
        }
    }

    private fun setupNavigation(binding: FragmentTeamsSetupBinding) {
        binding.apply {
            teamListSettingsButton.setOnClickListener { navigate(gameSettings()) }
            teamListBackButton.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun setupView(binding: FragmentTeamsSetupBinding) {
        setupAddNewTeamButton(binding)
    }

    private fun setupAddNewTeamButton(binding: FragmentTeamsSetupBinding) {
        binding.teamListAddTeamButton.setOnClickListener {
            val addTeamDialog = UpdateTeamDialogFragment()
            addTeamDialog.setTargetFragment(this, UpdateTeamDialogRequestCode.REQUEST_NEW.code)
            addTeamDialog.show(parentFragmentManager, "addTeamDialog")
        }
    }

    private fun observeTeams(teamsContainer: ViewGroup, inflater: LayoutInflater) {
        teamsSetupViewModel.teams.observe(viewLifecycleOwner) { teams ->
            teamsContainer.removeAllViews()
            teams.forEach { team ->
                val teamBinding = ViewTeamListItemBinding.inflate(
                    inflater, teamsContainer, true
                )
                teamBinding.team = team
                initTeamItemButtons(team, teamBinding)
            }
        }
    }

    private fun initTeamItemButtons(team: Team, binding: ViewTeamListItemBinding) {
        binding.teamListItemDeleteTeamButton.setOnClickListener {
            when (teamsSetupViewModel.canDeleteTeam()) {
                AT_LEAST_TWO_TEAMS_REQUIRED -> AlertDialog.Builder(requireContext())
                    .setMessage(R.string.delete_team_dialog_not_enough_teams)
                    .setPositiveButton(
                        R.string.ok,
                        dismissDialog
                    )
                    .show()
                else -> AlertDialog.Builder(requireContext())
                    .setMessage(R.string.delete_team_dialog_message)
                    .setPositiveButton(R.string.delete) { _, _ ->
                        teamsSetupViewModel.deleteTeam(
                            team.id
                        )
                    }
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
            addTeamDialog.show(parentFragmentManager, "editTeamDialog")
        }
    }
}