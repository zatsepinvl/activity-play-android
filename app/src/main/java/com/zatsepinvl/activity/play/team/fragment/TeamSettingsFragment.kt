package com.zatsepinvl.activity.play.team.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.zatsepinvl.activity.play.databinding.FragmentTeamSettingsBinding
import com.zatsepinvl.activity.play.databinding.ViewTeamSettingsItemBinding
import com.zatsepinvl.activity.play.di.ViewModelAwareFragment
import com.zatsepinvl.activity.play.team.DeleteTeamErrorCode.AT_LEAST_TWO_TEAMS_REQUIRED
import com.zatsepinvl.activity.play.team.TeamSettingsViewModel

class TeamSettingsFragment :
    ViewModelAwareFragment<TeamSettingsViewModel>(TeamSettingsViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamSettingsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val teamSettingsItemRoot = binding.teamSettingsItemRoot
        viewModel.teams.observe(viewLifecycleOwner, Observer { teams ->
            teamSettingsItemRoot.removeAllViews()
            teams.forEach { team ->
                val teamBinding = ViewTeamSettingsItemBinding.inflate(
                    inflater, teamSettingsItemRoot, true
                )
                teamBinding.team = team
                teamBinding.viewmodel = viewModel
            }
        })

        viewModel.deleteTeamErrorEvent.observe(viewLifecycleOwner, Observer {
            when (it!!) {
                AT_LEAST_TWO_TEAMS_REQUIRED -> AlertDialog.Builder(activity!!).setMessage(
                    "You can not delete team as at least two teams are required for game."
                )
                    .setNeutralButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        })

        return binding.root
    }
}