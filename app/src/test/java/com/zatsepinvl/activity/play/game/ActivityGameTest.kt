package com.zatsepinvl.activity.play.game

import com.zatsepinvl.activity.play.core.*
import createTestGame
import org.junit.Assert.*
import org.junit.Test

class ActivityGameTest {
    @Test
    fun validate_init_state() {
        val game = createTestGame()

        assertEquals(
            "The initial score should be 0",
            listOf(0, 0),
            listOf(
                game.getTeamTotalScore(0),
                game.getTeamTotalScore(1)
            )
        )

        assertEquals(
            "The initial score should be 0",
            0, game.getTeamTotalScore(0)
        )
    }

    @Test
    fun validate_current_task() {
        val game = createTestGame()

        val taskBefore = game.currentTask
        game.startRound()
        val taskDuring = game.currentTask
        game.finishRound()
        val taskAfter = game.currentTask

        assertNull(taskBefore)
        assertNotNull(taskDuring)
        assertNull(taskAfter)
    }

    @Test
    fun play_1_frame() {
        val game = createTestGame(
            GameSettings(
                pointsForDone = 1,
                pointsForFail = 0
            )
        )

        //team 1 then 2
        game.playOneFrame(doneCount = 2, failCount = 1)
        game.playOneFrame(doneCount = 3, failCount = 1)
        //team 1 then 2
        game.playOneFrame(doneCount = 2, failCount = 1)
        game.playOneFrame(doneCount = 3, failCount = 1)

        assertEquals(4, game.getTeamTotalScore(0))
        assertEquals(6, game.getTeamTotalScore(1))
    }

    @Test
    fun finish_game() {
        val game = createTestGame(GameSettings(maxScore = 2))

        game.playOneFrame(doneCount = 1)
        game.playOneFrame(doneCount = 2)

        assertTrue(game.finished)
        assertEquals(1, game.getWinnerTeamIndex())
    }

    @Test
    fun save_then_restore_state() {
        val settings = GameSettings(maxScore = 2)
        val game = createTestGame(settings)

        game.playOneFrame(doneCount = 1)
        val state = game.save()
        val loadedGame = createTestGame(settings).apply { load(state) }

        loadedGame.playOneFrame(doneCount = 2)

        assertTrue(loadedGame.finished)
        assertEquals(1, loadedGame.getWinnerTeamIndex())
    }

    @Test
    fun test_all_words_are_different() {
        val wordsRange = 1..1000
        val game = createTestGame(
            dictionary = Dictionary(
                "en",
                wordsRange.map { noun("word#$it") }.toList(),
                defaultRetryCount = 10000
            )
        )
        game.startRound()
        val usedWords = mutableListOf<Word>()
        repeat(wordsRange.count()) {
            val word = game.currentTask!!.word
            assertFalse(
                "$word has not been used earlier",
                usedWords.contains(word)
            )
            usedWords.add(word)
            game.completeCurrentTask()
        }
    }

    @Test
    fun test_actions_different_between_frames() {
        val game = createTestGame()

        val actions = mutableSetOf<GameAction>()

        //many iterations to cover random probability
        repeat(100) {
            val action = game.startRound().action
            game.finishRound()
            actions.add(action)
        }

        assertEquals(GameAction.values().toSet(), actions)
    }


    @Test
    fun skip_single_task() {
        val game = createTestGame()

        game.startRound()
        game.finishRound()

        assertEquals(1, game.getTeamCompletedTasks(0).size)
    }

    private fun ActivityGame.playOneFrame(doneCount: Int = 0, failCount: Int = 0) {
        this.startRound()
        repeat(doneCount) { this.completeCurrentTask() }
        repeat(failCount) { this.skipCurrentTask() }
        this.finishRound()
    }

}

