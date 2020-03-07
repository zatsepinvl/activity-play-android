package com.zatsepinvl.activity.play.game.service

import com.zatsepinvl.activity.play.core.ActivityGame
import com.zatsepinvl.activity.play.core.model.GameState
import com.zatsepinvl.activity.play.dictionary.DictionaryHolder
import com.zatsepinvl.activity.play.settings.service.GameSettingsService
import com.zatsepinvl.activity.play.team.model.Team
import com.zatsepinvl.activity.play.team.service.TeamService
import javax.inject.Inject

interface GameService {
    fun createNewGame(): ActivityGame
    fun saveGame(game: ActivityGame)
    fun getSavedGame(): ActivityGame
    fun isGameSaved(): Boolean
    fun startNewGame()
    fun currentTeam(): Team
}

class GameServiceImpl @Inject constructor(
    private val gameStateRepository: GameStateRepository,
    private val gameSettingsService: GameSettingsService,
    private val dictionaryHolder: DictionaryHolder,
    private val teamService: TeamService
) : GameService {

    override fun startNewGame() {
        saveGame(createNewGame())
    }

    override fun createNewGame(): ActivityGame {
        return createGame()
    }

    private fun createGame(state: GameState? = null): ActivityGame {
        return ActivityGame(
            gameSettingsService.getSettings(),
            dictionaryHolder.getDictionary(),
            state
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
        return createGame(gameStateRepository.get()!!)
    }

    override fun isGameSaved(): Boolean {
        return gameStateRepository.exists()
    }

    override fun currentTeam(): Team {
        return teamService.getTeams()[getSavedGame().currentTeamIndex]
    }


}