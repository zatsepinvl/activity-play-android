package com.zatsepinvl.activity.play.team.service

import com.zatsepinvl.activity.play.color.ColorId
import com.zatsepinvl.activity.play.color.ColorService
import com.zatsepinvl.activity.play.team.model.Team
import javax.inject.Inject

interface TeamService {
    fun updateTeam(team: Team)
    fun updateTeam(id: String, name: String, colorId: ColorId)
    fun deleteTeam(id: String)
    fun addTeam(team: Team)
    fun addTeam(name: String, colorId: ColorId)
    fun getTeams(): List<Team>
    fun getTeamsCount(): Int
    fun createTeam(name: String, colorId: ColorId): Team
}

class TeamServiceImpl @Inject constructor(
    private val teamRepository: TeamRepository,
    private val colorService: ColorService
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

    override fun updateTeam(team: Team) {
        teamRepository.delete(team.id)
        teamRepository.save(team)
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
            ?.map { it.copy(color = colorService.getColorById(it.colorId).hexCode) }
            ?.sortedBy { it.index }
            ?: throw IllegalStateException("There is no one team created yet")
    }

    override fun getTeamsCount(): Int {
        return teamRepository.count()
    }

}