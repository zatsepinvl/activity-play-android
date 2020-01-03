package com.zatsepinvl.activity.play.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.core.GameTask
import javax.inject.Inject

class TeamSettingsViewModel @Inject constructor(
    private val teamService: TeamService
) : ViewModel() {

    val teams = MutableLiveData<List<Team>>()

    init {
        if (teamService.getTeamsCount() == 0) {
            teamService.createDefaultTeams().forEach { teamService.addTeam(it) }
        }
        teams.value = teamService.getTeams()
    }
}