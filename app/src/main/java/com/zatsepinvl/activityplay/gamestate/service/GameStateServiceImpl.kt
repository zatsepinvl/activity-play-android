package com.zatsepinvl.activityplay.gamestate.service

import com.zatsepinvl.activityplay.core.model.GameState
import com.zatsepinvl.activityplay.core.model.GameStateFactory
import com.zatsepinvl.activityplay.gamestate.repository.GameStateRepository
import javax.inject.Inject

class GameStateServiceImpl @Inject constructor(
    private val gameStateRepository: GameStateRepository,
) : GameStateService {

    override fun defaultGameState(): GameState {
        return GameStateFactory.defaultGameState()
    }

    override fun saveGame(gameState: GameState) {
        gameStateRepository.save(gameState)
    }

    override fun getSavedGame(): GameState {
        check(isGameSaved()) {
            "Game has not been saved yet. Create it and save before using this method."
        }
        return gameStateRepository.get()!!
    }

    override fun isGameSaved(): Boolean {
        return gameStateRepository.exists()
    }

}