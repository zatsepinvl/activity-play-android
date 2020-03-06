package com.zatsepinvl.activity.play.game.service

import android.content.Context
import android.graphics.drawable.Drawable
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.core.model.GameAction
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

