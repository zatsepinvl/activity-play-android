package com.zatsepinvl.activityplay.team.service

import android.content.Context
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.color.ColorId
import com.zatsepinvl.activityplay.color.ColorService
import com.zatsepinvl.activityplay.team.model.Team
import javax.inject.Inject
import javax.inject.Singleton

interface TeamService {
    fun updateTeam(team: Team)
    fun updateTeam(id: String, name: String, colorId: ColorId)
    fun deleteTeam(id: String)
    fun addTeam(team: Team)
    fun addTeam(name: String, colorId: ColorId)
    fun getTeams(): List<Team>
    fun getTeamsCount(): Int
    fun createTeam(name: String, colorId: ColorId): Team
    fun setupDefaultTeamsIfNeeded()
}

@Singleton
class TeamServiceImpl @Inject constructor(
    private val teamRepository: TeamRepository,
    private val colorService: ColorService,
    private val context: Context
) : TeamService {
    override fun createTeam(name: String, colorId: ColorId): Team {
        val index = getTeamsCount()
        return Team(
            getTeamsCount().toString(),
            index,
            name,
            colorId,
            colorService.getColorById(colorId).hexCode
        )
    }

    override fun setupDefaultTeamsIfNeeded() {
        if (getTeamsCount() == 0) {
            addTeam(context.getString(R.string.default_team_1_name), ColorId.GREEN)
            addTeam(context.getString(R.string.default_team_2_name), ColorId.RED)
        }
    }

    override fun updateTeam(team: Team) {
        teamRepository.update(team)
    }

    override fun updateTeam(id: String, name: String, colorId: ColorId) {
        val updatedTeam = teamRepository.getById(id)?.copy(
            name = name,
            colorId = colorId
        ) ?: throw IllegalArgumentException("Can not find team by id: $id")
        updateTeam(updatedTeam)
    }

    override fun deleteTeam(id: String) {
        teamRepository.delete(id)
    }

    override fun addTeam(team: Team) {
        teamRepository.save(team)
    }

    override fun addTeam(name: String, colorId: ColorId) {
        teamRepository.save(
            createTeam(name, colorId)
        )
    }

    override fun getTeams(): List<Team> {
        return teamRepository.getAll()
            ?.mapIndexed { index: Int, team: Team ->
                team.copy(
                    color = colorService.getColorById(team.colorId).hexCode,
                    index = index
                )
            }
            ?.sortedBy { it.index }
            ?: throw IllegalStateException("There is no one team created yet")
    }

    override fun getTeamsCount(): Int {
        return teamRepository.count()
    }

}