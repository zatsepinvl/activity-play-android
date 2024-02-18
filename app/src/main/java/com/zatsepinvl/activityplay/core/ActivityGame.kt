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
        require(settings.teamCount > 1) { "At least 2 teams are required" }
        require(settings.actions.isNotEmpty()) { "At least 1 action should be enabled" }
    }

    fun startRound(): GameTask {
        require(!roundIsPlaying) { "The last round should be finished to start it again" }
        require(!finished) { "Game is finished" }
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
            currentGameAction,
            currentTask,
            roundIsPlaying
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
        state.currentGameAction
            ?.takeIf { action -> settings.actions.contains(action) }
            ?.let { action -> currentGameAction = action }
        finished = state.finished
        currentTask = state.currentTask
        roundIsPlaying = state.roundIsPlaying ?: false
    }

    fun reset() {
        roundIsPlaying = false
    }

    fun resetCurrentTeam() {
        require(!roundIsPlaying) { "Can not change current team index while game is playing" }
        currentTeamIndex = 0
    }

    private fun completeCurrentTask(done: Boolean): GameTask {
        requireInGame()
        val status = if (done) DONE else SKIPPED
        val taskResult = TaskResult(
            score = taskStatusToScore(status),
            status = status
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

    fun updateCompletedTaskResult(task: CompletedTask, status: TaskResultStatus) {
        check(task.task.roundIndex == currentRoundIndex) {
            "Task can be updated only during the current playing round"
        }
        check(task.task.teamIndex == currentTeamIndex) {
            "Task can be updated only by the current playing team"
        }
        val taskIndex = completedTasks.indexOf(task)
        completedTasks[taskIndex] = task.copy(
            result = TaskResult(
                score = taskStatusToScore(status),
                status = status
            )
        )
    }

    fun getWinnerTeamIndex(): Int {
        require(finished) { "Game must be finished to getSettings winner" }
        return (0..settings.teamCount)
            .map { i -> i to getTeamTotalScore(i) }
            .maxBy { it.second }.first
    }

    fun getTeamResult(teamIndex: Int): TeamResult {
        return completedTasks
            .filter { it.task.teamIndex == teamIndex }
            .run(this::convertToTeamResult)
    }

    fun getTeamResult(teamIndex: Int, roundIndex: Int): TeamResult {
        return completedTasks
            .filter { it.task.teamIndex == teamIndex }
            .filter { it.task.roundIndex == roundIndex }
            .run(this::convertToTeamResult)
    }

    fun getTeamTotalScore(teamIndex: Int): Int {
        return getTeamResult(teamIndex).score
    }

    fun getCurrentTeamResultForCurrentRound(): TeamResult {
        return getTeamResult(currentTeamIndex, currentRoundIndex)
    }

    private fun convertToTeamResult(tasks: List<CompletedTask>): TeamResult {
        return TeamResult(tasks)
    }

    private fun taskStatusToScore(status: TaskResultStatus): Int {
        return when (status) {
            DONE -> settings.pointsForDone
            SKIPPED -> settings.pointsForFail
        }
    }

    private fun requireInGame() {
        require(roundIsPlaying) { "Round must be playing" }
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
        return dictionary.getRandomWord(getUsedWords())
    }
}