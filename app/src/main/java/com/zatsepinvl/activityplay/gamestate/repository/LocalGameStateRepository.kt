package com.zatsepinvl.activityplay.gamestate.repository

import android.content.Context
import com.zatsepinvl.activityplay.android.storage.LocalJsonObjectRepository
import com.zatsepinvl.activityplay.core.model.GameState
import javax.inject.Inject

private const val GAME_STATE_SP_NAME = "GameState"

class LocalGameStateRepository @Inject constructor(context: Context) :
    GameStateRepository,
    LocalJsonObjectRepository<GameState>(
        context,
        GAME_STATE_SP_NAME,
        GameState::class.java
    )