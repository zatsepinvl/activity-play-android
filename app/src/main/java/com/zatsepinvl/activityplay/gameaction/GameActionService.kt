package com.zatsepinvl.activityplay.gameaction

import android.graphics.drawable.Drawable
import com.zatsepinvl.activityplay.core.model.GameAction

interface GameActionService {
    fun getActionLocalName(action: GameAction): String
    fun getActionDrawable(action: GameAction): Drawable
}

