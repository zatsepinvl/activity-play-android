package com.zatsepinvl.activityplay.gamestate.service

import com.zatsepinvl.activityplay.core.model.GameState

interface GameStateService {
    fun defaultGameState(): GameState
    fun saveGame(gameState: GameState)
    fun getSavedGame(): GameState
    fun isGameSaved(): Boolean
}

