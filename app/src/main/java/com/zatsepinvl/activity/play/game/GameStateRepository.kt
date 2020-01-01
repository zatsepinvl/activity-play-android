package com.zatsepinvl.activity.play.game

import android.content.Context
import com.zatsepinvl.activity.play.core.GameState
import com.zatsepinvl.activity.play.android.LocalJsonObjectRepository
import com.zatsepinvl.activity.play.android.ObjectRepository
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

