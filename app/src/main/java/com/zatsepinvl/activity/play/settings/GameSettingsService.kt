package com.zatsepinvl.activity.play.settings

import com.zatsepinvl.activity.play.core.GameSettings
import com.zatsepinvl.activity.play.team.TeamService
import javax.inject.Inject

interface GameSettingsService {
    fun createSettings(): GameSettings
    fun saveSettings(gameSettings: GameSettings)
    fun getSettings(): GameSettings
}

class GameSettingsServiceImpl @Inject constructor(
    private val repository: GameSettingsRepository,
    private val teamService: TeamService
) : GameSettingsService {
    override fun createSettings(): GameSettings {
        return GameSettings(
            teamCount = teamService.getTeamsCount()
        )
    }

    override fun saveSettings(gameSettings: GameSettings) {
        repository.save(gameSettings)
    }

    override fun getSettings(): GameSettings {
        return repository.get()
            ?.run {
                copy(teamCount = teamService.getTeamsCount())
            } ?: createSettings()
    }

}