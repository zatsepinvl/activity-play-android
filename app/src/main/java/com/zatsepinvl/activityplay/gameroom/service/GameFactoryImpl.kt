package com.zatsepinvl.activityplay.gameroom.service

import com.zatsepinvl.activityplay.core.ActivityGame
import com.zatsepinvl.activityplay.dictionary.DictionaryHolder
import com.zatsepinvl.activityplay.gameroom.model.GameRoomState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameFactoryImpl @Inject constructor(
    private val dictionaryHolder: DictionaryHolder
) : GameFactory {

    override fun createGame(roomState: GameRoomState): ActivityGame {
        val dictionary = dictionaryHolder.loadDictionary(roomState.dictionaryLanguage)
        return ActivityGame(
            roomState.gameSettings,
            dictionary,
            roomState.gameState
        )
    }
}