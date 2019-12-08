package com.zatsepinvl.activity.play.core

import com.zatsepinvl.activity.play.core.GameAction.*
import com.zatsepinvl.activity.play.core.TaskResultStatus.DONE
import com.zatsepinvl.activity.play.core.TaskResultStatus.SKIPPED
import java.util.*

enum class GameAction {
    SAY, SHOW, DRAW
}

enum class TaskResultStatus {
    DONE, SKIPPED
}

data class GameSettings(
    val teamCount: Int = 2,
    val pointsForDone: Int = 1,
    val pointsForFail: Int = 0,
    val maxScore: Int = 20,
    val actions: Set<GameAction> = setOf(SAY, DRAW, SHOW)
)

data class CompletedTask(
    val task: GameTask,
    var result: TaskResult
)

data class GameTask(
    val frameId: String,
    val teamIndex: Int,
    val roundIndex: Int,
    val action: GameAction,
    val word: Word
)


data class TaskResult(
    val score: Int,
    val status: TaskResultStatus
)

data class GameState(
    val completedTasks: MutableList<CompletedTask>,
    val finished: Boolean,
    val currentRoundIndex: Int,
    val currentTeamIndex: Int
)

class ActivityGame(
    private var settings: GameSettings,
    private var dictionary: Dictionary
) {
    private val completedTasks = mutableListOf<CompletedTask>()
    private val random = Random()

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

    var currentFrameId = "${currentRoundIndex}:${currentTeamIndex}"

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
            state.completedTasks.forEach { add(it) }
        }
        currentRoundIndex = state.currentRoundIndex
        currentTeamIndex = state.currentTeamIndex
        finished = state.finished
    }

    private fun completeCurrentTask(done: Boolean): GameTask {
        require(roundIsPlaying) { "Round must be playing to complete task" }
        checkNotNull(currentTask) { "Current task must not be null" }
        val taskResult = TaskResult(
            score = if (done) settings.pointsForDone else settings.pointsForFail,
            status = if (done) DONE else SKIPPED
        )
        val task = CompletedTask(currentTask!!, taskResult)
        completedTasks.add(task)
        currentTask = nextTask()
        return currentTask!!
    }

    fun finishRound() {
        roundIsPlaying = false
        currentTask = null
        currentTeamIndex++

        val maxScore = (0..settings.teamCount)
            .map { i -> getTeamTotalScore(i) }
            .max() ?: 0
        val maxScoreReached = maxScore >= settings.maxScore
        val lastTeamPlayed = currentTeamIndex == settings.teamCount

        if (lastTeamPlayed) {
            currentTeamIndex = 0
            currentRoundIndex++
            finished = maxScoreReached
        }
    }

    fun getTeamTotalScore(teamIndex: Int): Int {
        return getTeamCompletedTasks(teamIndex).totalScore()
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

    private fun nextTask(): GameTask {
        return GameTask(
            currentFrameId,
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