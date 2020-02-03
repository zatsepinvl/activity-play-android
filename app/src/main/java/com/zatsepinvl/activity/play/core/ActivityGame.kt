package com.zatsepinvl.activity.play.core

import com.zatsepinvl.activity.play.core.model.*
import com.zatsepinvl.activity.play.core.model.TaskResultStatus.DONE
import com.zatsepinvl.activity.play.core.model.TaskResultStatus.SKIPPED
import java.util.*


class ActivityGame(
    settings: GameSettings,
    dictionary: Dictionary
) {
    private val completedTasks = mutableListOf<CompletedTask>()
    private val random = Random()

    var settings: GameSettings = settings
        private set
    var dictionary: Dictionary = dictionary
        private set

    init {
        validateSettings(settings)
    }

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

    var currentFrameId = currentTask?.frameId

    var roundIsPlaying = false
        private set

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
            currentTeamIndex
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
        finished = state.finished
    }

    fun resetCurrentTeam() {
        require(!roundIsPlaying) { "Can not change current team index while game is playing" }
        currentTeamIndex = 0
    }

    private fun completeCurrentTask(done: Boolean): GameTask {
        requireInGame()
        val score = when {
            done -> settings.pointsForDone
            getTeamRoundScore(
                currentTeamIndex,
                currentRoundIndex
            ) + settings.pointsForFail <= 0 -> 0
            else -> settings.pointsForFail
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
        completedTasks.add(
            CompletedTask(
                currentTask!!,
                TaskResult(0, SKIPPED)
            )
        )

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

    private fun nextTask(): GameTask {
        return GameTask(
            currentTeamIndex,
            currentRoundIndex,
            nextAction(),
            nextWord()
        )
    }

    private fun nextAction(): GameAction {
        if (completedTasks.isEmpty()) {
            return nextRandomAction()
        }
        val lastTask = completedTasks.last().task
        val nextRound = lastTask.frameId != currentFrameId
        return if (nextRound) {
            nextRandomAction()
        } else {
            lastTask.action
        }
    }

    private fun nextRandomAction(): GameAction {
        val index = random.nextInt(settings.actions.size)
        return settings.actions.elementAt(index)
    }

    private fun getUsedWords(): Set<Word> {
        return completedTasks.map { it.task.word }.toSet()
    }

    private fun nextWord(): Word {
        return dictionary.getRandomWord(getUsedWords())
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