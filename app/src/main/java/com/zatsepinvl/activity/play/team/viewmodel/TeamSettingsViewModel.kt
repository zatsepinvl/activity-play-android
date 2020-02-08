package com.zatsepinvl.activity.play.team.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.color.ColorId
import com.zatsepinvl.activity.play.team.model.Team
import com.zatsepinvl.activity.play.team.service.TeamService
import com.zatsepinvl.activity.play.team.viewmodel.DeleteTeamErrorCode.AT_LEAST_TWO_TEAMS_REQUIRED
import javax.inject.Inject

enum class DeleteTeamErrorCode {
    AT_LEAST_TWO_TEAMS_REQUIRED
}

class TeamSettingsViewModel @Inject constructor(
    private val teamService: TeamService
) : ViewModel() {

    val teams = MutableLiveData<List<Team>>()

    init {
        if (teamService.getTeamsCount() == 0) {
            teamService.addTeam("Watermelon", ColorId.GREEN)
            teamService.addTeam("Strawberry", ColorId.RED)
        }
        syncTeams()
    }

    fun canDeleteTeam(): DeleteTeamErrorCode? {
        if (teams.value!!.size == 2) {
            return AT_LEAST_TWO_TEAMS_REQUIRED
        }
        return null
    }

    fun deleteTeam(teamId: String) {
        teamService.deleteTeam(teamId)
        syncTeams()
    }

    fun addTeam(name: String, colorId: ColorId) {
        teamService.addTeam(name, colorId)
        syncTeams()
    }

    fun updateTeam(id: String, name: String, colorId: ColorId) {
        teamService.updateTeam(id, name, colorId)
        syncTeams()
    }

    private fun syncTeams() {
        teams.value = teamService.getTeams()
    }
}