package com.zatsepinvl.activityplay.settings.service

import android.content.Context
import com.zatsepinvl.activityplay.core.model.GameAction
import com.zatsepinvl.activityplay.core.model.GameSettings
import com.zatsepinvl.activityplay.settings.model.TimerSettings
import com.zatsepinvl.activityplay.team.service.TeamService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameSettingsServiceImpl @Inject constructor(
    private val context: Context,
    private val teamService: TeamService
) : GameSettingsService {

    override fun getGameSettings(): GameSettings {
        return ActivityPlayPreference.getActivityPlayPreferences(
            context
        ).run {
            val actions = if (enabledActions.isNotEmpty()) {
                enabledActions
            } else {
                GameAction.values().toList()
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

    override fun getTimerSettings(): TimerSettings {
        val roundTimeSeconds = ActivityPlayPreference.getActivityPlayPreferences(
            context
        ).roundTimeSeconds
        return TimerSettings(roundTimeSeconds)
    }

}