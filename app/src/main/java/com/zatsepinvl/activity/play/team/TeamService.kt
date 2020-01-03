package com.zatsepinvl.activity.play.team

import com.zatsepinvl.activity.play.color.ColorId
import com.zatsepinvl.activity.play.color.ColorService
import javax.inject.Inject

data class Team(
    val id: String,
    val index: Int,
    val name: String,
    val colorId: ColorId,
    val colorResource: Int
)

interface TeamService {
    fun createDefaultTeams(): List<Team>
    fun editTeam(team: Team)
    fun deleteTeam(id: String)
    fun addTeam(team: Team)
    fun getTeams(): List<Team>
    fun getTeamsCount(): Int
}

class TeamServiceImpl @Inject constructor(
    private val teamRepository: TeamRepository,
    private val colorService: ColorService
) : TeamService {

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

    override fun getTeams(): List<Team> {
        return teamRepository.getAll()
            ?.map { it.copy(colorResource = colorService.getColorResourceById(it.colorId)) }
            ?: throw IllegalStateException("There is no one team created yet")
    }

    override fun getTeamsCount(): Int {
        return teamRepository.count()
    }

    override fun createDefaultTeams(): List<Team> {
        return listOf(
            Team(
                "0", 0,
                "Watermelon",
                ColorId.GREEN, colorService.getColorResourceById(ColorId.GREEN)
            ),
            Team(
                "1", 1,
                "Strawberry",
                ColorId.RED, colorService.getColorResourceById(ColorId.RED)
            )
        )
    }


}