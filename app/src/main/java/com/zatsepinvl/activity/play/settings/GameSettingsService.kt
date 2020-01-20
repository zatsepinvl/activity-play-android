package com.zatsepinvl.activity.play.settings

import android.content.Context
import com.zatsepinvl.activity.play.core.GameSettings
import com.zatsepinvl.activity.play.team.TeamService
import javax.inject.Inject

interface GameSettingsService {
    fun getSettings(): GameSettings
}

class GameSettingsServiceImpl @Inject constructor(
    private val context: Context,
    private val teamService: TeamService
) : GameSettingsService {

    override fun getSettings(): GameSettings {
        return ActivityPlayPreference.getActivityPlayPreferences(context).run {
            GameSettings(
                teamCount = teamService.getTeamsCount(),
                maxScore = maxScore,
                pointsForFail = if (fineForSkipping) -1 else 0,
                pointsForDone = 1,
                actions = enabledActions
            )
        }
    }

}