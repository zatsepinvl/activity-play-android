package com.zatsepinvl.activity.play.team

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.android.SingleLiveEvent
import com.zatsepinvl.activity.play.color.ColorId
import com.zatsepinvl.activity.play.team.DeleteTeamErrorCode.AT_LEAST_TWO_TEAMS_REQUIRED
import javax.inject.Inject

enum class DeleteTeamErrorCode {
    AT_LEAST_TWO_TEAMS_REQUIRED
}

class TeamSettingsViewModel @Inject constructor(
    private val teamService: TeamService
) : ViewModel() {

    val teams = MutableLiveData<List<Team>>()
    val deleteTeamErrorEvent = SingleLiveEvent<DeleteTeamErrorCode>()


    init {
        if (teamService.getTeamsCount() == 0) {
            teamService.addTeam("Watermelon", ColorId.GREEN)
            teamService.addTeam("Strawberry", ColorId.RED)
        }
        syncTeams()
    }

    fun deleteTeam(teamId: String) {
        if (teams.value!!.size == 2) {
            deleteTeamErrorEvent.call(AT_LEAST_TWO_TEAMS_REQUIRED)
            return
        }
        teamService.deleteTeam(teamId)
        syncTeams()
    }

    fun addTeam(name: String, colorId: ColorId) {
        teamService.addTeam(name, colorId)
        syncTeams()
    }

    private fun syncTeams() {
        teams.value = teamService.getTeams()
    }
}