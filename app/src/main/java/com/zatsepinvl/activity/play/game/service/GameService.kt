package com.zatsepinvl.activity.play.game.service

import com.zatsepinvl.activity.play.core.ActivityGame
import com.zatsepinvl.activity.play.dictionary.DictionaryService
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
    private val dictionaryService: DictionaryService,
    private val teamService: TeamService
) : GameService {

    override fun startNewGame() {
        saveGame(createNewGame())
    }

    override fun createNewGame(): ActivityGame {
        return ActivityGame(
            gameSettingsService.getSettings(),
            dictionaryService.loadDictionary()
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

    override fun currentTeam(): Team {
        return teamService.getTeams()[getSavedGame().currentTeamIndex]
    }


}