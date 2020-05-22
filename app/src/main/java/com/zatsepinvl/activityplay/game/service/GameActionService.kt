package com.zatsepinvl.activityplay.game.service

import android.content.Context
import android.graphics.drawable.Drawable
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.core.model.GameAction
import javax.inject.Inject

interface GameActionService {
    fun getActionLocalName(action: GameAction): String
    fun getActionDrawable(action: GameAction): Drawable
}

class GameActionServiceImpl @Inject constructor(
    private val context: Context
) : GameActionService {

    override fun getActionLocalName(action: GameAction): String {
        return when (action) {
            GameAction.SAY -> context.getString(R.string.game_action_say)
            GameAction.SHOW -> context.getString(R.string.game_action_show)
            GameAction.DRAW -> context.getString(R.string.game_action_draw)

        }
    }

    override fun getActionDrawable(action: GameAction): Drawable {
        return when (action) {
            GameAction.SAY -> context.getDrawable(R.drawable.karaoke)!!
            GameAction.SHOW -> context.getDrawable(R.drawable.theater)!!
            GameAction.DRAW -> context.getDrawable(R.drawable.art)!!
        }
    }
}

