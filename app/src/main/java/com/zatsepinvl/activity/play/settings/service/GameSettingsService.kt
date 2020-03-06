package com.zatsepinvl.activity.play.settings.service

import android.content.Context
import com.zatsepinvl.activity.play.core.model.GameAction
import com.zatsepinvl.activity.play.core.model.GameSettings
import com.zatsepinvl.activity.play.team.service.TeamService
import javax.inject.Inject
import javax.inject.Singleton

interface GameSettingsService {
    fun getSettings(): GameSettings
    fun getSecondsForRound(): Int
}

@Singleton
class GameSettingsServiceImpl @Inject constructor(
    private val context: Context,
    private val teamService: TeamService
) : GameSettingsService {
    override fun getSecondsForRound(): Int {
        return ActivityPlayPreference.getActivityPlayPreferences(
            context
        ).roundTimeSeconds
    }

    override fun getSettings(): GameSettings {
        return ActivityPlayPreference.getActivityPlayPreferences(
            context
        ).run {
            val actions = if (enabledActions.isNotEmpty()) {
                enabledActions
            } else {
                GameAction.values().toSet()
            }
            GameSettings(
                teamCount = teamService.getTeamsCount(),
                maxScore = maxScore,
                pointsForFail = if (fineForSkipping) -1 else 0,
                pointsForDone = 1,
                actions = actions
            )
        }
    }

}