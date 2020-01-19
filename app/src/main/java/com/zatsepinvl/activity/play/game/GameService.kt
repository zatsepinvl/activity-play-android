package com.zatsepinvl.activity.play.game

import com.zatsepinvl.activity.play.core.ActivityGame
import com.zatsepinvl.activity.play.settings.GameSettingsService
import javax.inject.Inject

interface GameService {
    fun createNewGame(): ActivityGame
    fun saveGame(game: ActivityGame)
    fun getSavedGame(): ActivityGame
    fun isGameSaved(): Boolean
    fun startNewGame()
}

class GameServiceImpl @Inject constructor(
    private val gameStateRepository: GameStateRepository,
    private val gameSettingsService: GameSettingsService,
    private val dictionaryService: DictionaryService
) : GameService {

    override fun startNewGame() {
        saveGame(createNewGame())
    }

    override fun createNewGame(): ActivityGame {
        return ActivityGame(
            gameSettingsService.getSettings(),
            dictionaryService.getDictionary()
        )
    }

    override fun saveGame(game: ActivityGame) {
        val gameState = game.save()
        gameStateRepository.save(gameState)
    }

    override fun getSavedGame(): ActivityGame {
        check(isGameSaved()) {
            "Game has not been saved yet. Create it and save before using this method."
        }
        val game = createNewGame()
        gameStateRepository.get()!!.apply(game::load)
        return game
    }

    override fun isGameSaved(): Boolean {
        return gameStateRepository.exists()
    }


}