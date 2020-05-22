package com.zatsepinvl.activityplay.game.service

import android.content.Context
import com.zatsepinvl.activityplay.android.storage.LocalJsonObjectRepository
import com.zatsepinvl.activityplay.android.storage.ObjectRepository
import com.zatsepinvl.activityplay.core.model.GameState
import javax.inject.Inject

interface GameStateRepository :
    ObjectRepository<GameState>

private const val GAME_STATE_SP_NAME = "GameState"

class LocalGameStateRepository @Inject constructor(context: Context) :
    GameStateRepository,
    LocalJsonObjectRepository<GameState>(
        context,
        GAME_STATE_SP_NAME,
        GameState::class.java
    )

