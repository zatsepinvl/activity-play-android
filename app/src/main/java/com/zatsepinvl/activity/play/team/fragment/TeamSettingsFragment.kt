package com.zatsepinvl.activity.play.team.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zatsepinvl.activity.play.databinding.FragmentTeamSettingsBinding
import com.zatsepinvl.activity.play.databinding.ViewTeamSettingsItemBinding
import com.zatsepinvl.activity.play.di.ViewModelAwareFragment
import com.zatsepinvl.activity.play.team.TeamSettingsViewModel

class TeamSettingsFragment :
    ViewModelAwareFragment<TeamSettingsViewModel>(TeamSettingsViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamSettingsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val teamSettingsItemRoot = binding.teamSettingsItemRoot
        viewModel.teams.observe({ this.lifecycle }) { teams ->
            teamSettingsItemRoot.removeAllViews()
            teams.forEach { team ->
                val teamBinding = ViewTeamSettingsItemBinding.inflate(inflater)
                teamBinding.team = team
                teamBinding.viewmodel = viewModel
                teamSettingsItemRoot.addView(teamBinding.root)
            }
        }
        return binding.root
    }
}