package com.zatsepinvl.activityplay.core

import com.zatsepinvl.activityplay.core.model.*
import com.zatsepinvl.activityplay.core.model.TaskResultStatus.DONE
import com.zatsepinvl.activityplay.core.model.TaskResultStatus.SKIPPED
import java.util.*


class ActivityGame(
    settings: GameSettings,
    dictionary: Dictionary,
    state: GameState? = null
) {
    private val completedTasks = mutableListOf<CompletedTask>()
    private val random = Random()

    var settings: GameSettings = settings
        private set
    var dictionary: Dictionary = dictionary
        private set

    var finished = false
        private set

    /**
     * currentTask is NOT null only during the playing round
     */
    var currentTask: GameTask? = null
        private set

    var currentRoundIndex = 0
        private set

    var currentTeamIndex = 0
        private set

    var currentGameAction: GameAction = randomAction()
        private set

    var currentFrameId = currentTask?.frameId

    var roundIsPlaying = false
        private set

    init {
        validateSettings(settings)
        state?.apply(::load)
    }

    fun resetSettings(settings: GameSettings) {
        validateSettings(settings)
        this.settings = settings
    }

    fun resetDictionary(dictionary: Dictionary) {
        this.dictionary = dictionary
    }

    private fun validateSettings(settings: GameSettings) {
        require(settings.teamCount > 1) { "At least 2 teams are required." }
        require(settings.actions.isNotEmpty()) { "At least 1 action should be enabled" }
    }

    fun startRound(): GameTask {
        require(!roundIsPlaying)
        require(!finished)
        roundIsPlaying = true
        currentTask = nextTask()
        return currentTask!!
    }

    fun completeCurrentTask(): GameTask {
        return completeCurrentTask(true)
    }

    fun skipCurrentTask(): GameTask {
        return completeCurrentTask(false)
    }

    fun save(): GameState {
        return GameState(
            completedTasks,
            finished,
            currentRoundIndex,
            currentTeamIndex,
            currentGameAction
        )
    }

    fun load(state: GameState) {
        require(!roundIsPlaying)
        completedTasks.apply {
            clear()
            addAll(state.completedTasks)
        }
        currentRoundIndex = state.currentRoundIndex
        currentTeamIndex = state.currentTeamIndex
        if (state.currentGameAction != null && settings.actions.contains(state.currentGameAction)) {
            currentGameAction = state.currentGameAction
        }
        finished = state.finished
    }

    fun resetCurrentTeam() {
        require(!roundIsPlaying) { "Can not change current team index while game is playing" }
        currentTeamIndex = 0
    }

    private fun completeCurrentTask(done: Boolean): GameTask {
        requireInGame()
        val score = if (done) {
            settings.pointsForDone
        } else {
            if (getTeamRoundScore(currentTeamIndex, currentRoundIndex) > 0) {
                settings.pointsForFail
            } else {
                0
            }
        }
        val taskResult = TaskResult(
            score = score,
            status = if (done) DONE else SKIPPED
        )
        val task = CompletedTask(currentTask!!, taskResult)
        completedTasks.add(task)
        currentTask = nextTask()
        return currentTask!!
    }

    fun finishRound() {
        requireInGame()
        roundIsPlaying = false
        currentTask = null

        currentTeamIndex++
        val lastTeamPlayed = currentTeamIndex == settings.teamCount

        if (lastTeamPlayed) {
            val scores = (0..settings.teamCount)
                .map { i -> getTeamTotalScore(i) }
                .sortedDescending()
            val maxScore = scores[0]
            val anotherMaxScore = scores[1]

            val maxScoreReached = maxScore >= settings.maxScore && maxScore != anotherMaxScore

            currentTeamIndex = 0
            currentRoundIndex++
            currentGameAction = nextAction()
            finished = maxScoreReached
        }
    }

    fun getTeamTotalScore(teamIndex: Int): Int {
        return getTeamCompletedTasks(teamIndex).totalScore()
    }

    fun getTeamRoundScore(teamIndex: Int, roundIndex: Int): Int {
        return getTeamCompletedTasks(teamIndex)
            .filter { it.task.roundIndex == roundIndex }
            .sumBy { it.result.score }
    }

    fun getWinnerTeamIndex(): Int {
        require(finished) { "Game must be finished to getSettings winner" }
        return (0..settings.teamCount)
            .map { i -> i to getTeamTotalScore(i) }
            .maxBy { it.second }?.first ?: 0
    }

    fun getTeamCompletedTasks(teamIndex: Int): List<CompletedTask> {
        return completedTasks.filter { it.task.teamIndex == teamIndex }
    }

    private fun requireInGame() {
        require(roundIsPlaying) { "Round must be playing to complete task" }
        checkNotNull(currentTask) { "Current task must not be null" }
    }

    private fun nextAction(): GameAction {
        val actions = settings.actions.filter { it !== currentGameAction }
        if (actions.isEmpty()) return currentGameAction
        val index = random.nextInt(actions.size)
        return actions.elementAt(index)
    }

    private fun nextTask(): GameTask {
        return GameTask(
            currentTeamIndex,
            currentRoundIndex,
            currentGameAction,
            nextWord()
        )
    }

    private fun randomAction(): GameAction {
        val index = random.nextInt(settings.actions.size)
        return settings.actions.elementAt(index)
    }

    private fun getUsedWords(): Set<Word> {
        return completedTasks
            .filter { it.result.status == DONE }
            .map { it.task.word }
            .toSet()
    }

    private fun nextWord(): Word {
        return try {
            dictionary.getRandomWord(getUsedWords())
        } catch (ex: NoWordsFoundException) {
            //should never happen
            dictionary.getRandomWord(setOf())
        }
    }
}


fun List<CompletedTask>.totalScore(): Int {
    return this.sumBy { it.result.score }
}

fun List<CompletedTask>.totalScoreForRound(roundIndex: Int): Int {
    return this.filter { it.task.roundIndex == roundIndex }
        .sumBy { it.result.score }
}

fun List<CompletedTask>.totalScoreForLastRound(): Int {
    val lastRound: Int = this.map { it.task.roundIndex }.max() ?: 0
    return totalScoreForRound(lastRound)
}