package com.zatsepinvl.activity.play.team

import android.content.Context
import com.zatsepinvl.activity.play.android.*
import javax.inject.Inject

interface TeamRepository {
    fun save(team: Team)
    fun delete(id: String)
    fun getById(id: String): Team?
    fun exists(id: String): Boolean
    fun getAll(): List<Team>?
    fun count(): Int
}

private const val TEAMS_SP_NAME = "Teams"

private data class TeamsWrapper(
    val teams: MutableList<Team>
)

class LocalTeamRepository @Inject constructor(
    private val context: Context
) : TeamRepository {

    private val wrapper: TeamsWrapper

    init {
        wrapper = context.privateSp(TEAMS_SP_NAME).getFromJson(TeamsWrapper::class.java)
            ?: TeamsWrapper(mutableListOf())
    }

    override fun save(team: Team) {
        wrapper.teams.add(team)
        saveWrapper()
    }

    override fun delete(id: String) {
        val index = wrapper.teams.indexOfFirst { it.id == id }
        require(index != -1) { "Team by id $id has no been found" }
        wrapper.teams.removeAt(index)
        saveWrapper()
    }

    override fun getById(id: String): Team? {
        return wrapper.teams.find { it.id == id }
    }

    override fun exists(id: String): Boolean {
        return getById(id) != null
    }

    override fun getAll(): List<Team>? {
        if (count() == 0) return null
        return wrapper.teams.toList()
    }

    override fun count(): Int {
        return wrapper.teams.size
    }

    private fun saveWrapper() {
        context.privateSp(TEAMS_SP_NAME).saveJson(wrapper)
    }

}

