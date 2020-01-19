package com.zatsepinvl.activity.play.team

import com.zatsepinvl.activity.play.color.ColorId
import com.zatsepinvl.activity.play.color.ColorService
import javax.inject.Inject

data class Team(
    val id: String,
    val index: Int,
    val name: String,
    val colorId: ColorId,
    val color: Int
)

interface TeamService {
    fun editTeam(team: Team)
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

    override fun editTeam(team: Team) {
        teamRepository.delete(team.id)
        teamRepository.save(team)
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
            ?: throw IllegalStateException("There is no one team created yet")
    }

    override fun getTeamsCount(): Int {
        return teamRepository.count()
    }

}