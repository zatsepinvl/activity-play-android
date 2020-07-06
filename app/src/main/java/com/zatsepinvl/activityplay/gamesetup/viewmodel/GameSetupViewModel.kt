package com.zatsepinvl.activityplay.gamesetup.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.color.ColorId
import com.zatsepinvl.activityplay.gamesetup.viewmodel.DeleteTeamErrorCode.AT_LEAST_TWO_TEAMS_REQUIRED
import com.zatsepinvl.activityplay.team.model.Team
import com.zatsepinvl.activityplay.team.service.TeamService
import javax.inject.Inject

enum class DeleteTeamErrorCode {
    AT_LEAST_TWO_TEAMS_REQUIRED
}

class TeamsSetupViewModel @Inject constructor(
    private val teamService: TeamService,
    context: Context
) : ViewModel() {

    val teams = MutableLiveData<List<Team>>()

    init {
        if (teamService.getTeamsCount() == 0) {
            teamService.addTeam(context.getString(R.string.default_team_1_name), ColorId.GREEN)
            teamService.addTeam(context.getString(R.string.default_team_2_name), ColorId.RED)
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