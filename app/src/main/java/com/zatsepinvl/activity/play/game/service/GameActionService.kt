package com.zatsepinvl.activity.play.game.service

import android.content.Context
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.core.model.GameAction
import javax.inject.Inject

interface GameActionService {
    fun getActionLocalName(action: GameAction): String
}

class GameActionServiceImpl @Inject constructor(
    private val context: Context
) : GameActionService {
    override fun getActionLocalName(action: GameAction): String {
        return when (action) {
            GameAction.DRAW -> context.getString(R.string.game_action_draw)
            GameAction.SHOW -> context.getString(R.string.game_action_show)
            GameAction.SAY -> context.getString(R.string.game_action_say)
        }
    }
}

