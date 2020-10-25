package com.zatsepinvl.activityplay.gameaction

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.core.model.GameAction
import javax.inject.Inject

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
            GameAction.SAY -> ContextCompat.getDrawable(context, R.drawable.karaoke)!!
            GameAction.SHOW -> ContextCompat.getDrawable(context, R.drawable.theater)!!
            GameAction.DRAW -> ContextCompat.getDrawable(context, R.drawable.art)!!
        }
    }
}