package com.zatsepinvl.activityplay.core

import com.zatsepinvl.activityplay.core.model.*
import com.zatsepinvl.activityplay.core.model.TaskResultStatus.*
import java.util.*


class ActivityGame(
    settings: GameSettings,
    dictionary: Dictionary,
    state: GameState? = null
) {
    private val random = Random()

    private val _usedWords = mutableSetOf<Word>()
    private val _completedTasks = mutableListOf<CompletedTask>()
    private val _teamScores = mutableMapOf<String, Int>()

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

    var roundIsPlaying = false
        private set

    val usedWords get() = _usedWords.toList()
    val completedTasks get() = _completedTasks.toList()
    val teamScores get() = _teamScores

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
        _completedTasks.clear()
        roundIsPlaying = true
        currentTask = nextTask()
        return currentTask!!
    }

    fun save(): GameState {
        return GameState(
            _completedTasks,
            _teamScores,
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
        _completedTasks.apply {
            clear()
            addAll(state.completedTasks)
        }
        state.teamScores.let { scores ->
            _teamScores.clear()
            _teamScores.putAll(scores)
        }
        currentRoundIndex = state.currentRoundIndex
        currentTeamIndex = state.currentTeamIndex
        state.currentGameAction
            ?.takeIf { action -> settings.actions.contains(action) }
            ?.let { action -> currentGameAction = action }
        finished = state.finished
        currentTask = state.currentTask
        roundIsPlaying = state.roundIsPlaying
    }

    fun reset() {
        roundIsPlaying = false
    }

    fun resetCurrentTeam() {
        require(!roundIsPlaying) { "Can not change current team index while game is playing" }
        currentTeamIndex = 0
    }

    fun completeCurrentTask(): GameTask {
        return completeCurrentTask(DONE)
    }

    fun failCurrentTask(): GameTask {
        return completeCurrentTask(FAILED)
    }

    fun skipCurrentTask(): GameTask {
        return completeCurrentTask(SKIPPED)
    }

    private fun completeCurrentTask(status: TaskResultStatus): GameTask {
        requireInGame()
        val taskResult = TaskResult(
            score = taskStatusToScore(status),
            status = status
        )
        val task = CompletedTask(currentTask!!, taskResult)
        if (task.result.status == DONE) {
            _usedWords.add(task.task.word)
        }
        _completedTasks.add(task)
        currentTask = nextTask()
        return currentTask!!
    }

    fun finishRound() {
        requireInGame()
        updateCurrentTeamTotalScore()
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

    private fun updateCurrentTeamTotalScore() {
        val plusScore = getCurrentTeamRoundResult().score
        val scoreKey = currentTeamIndex.toString()
        val currentScore = _teamScores[scoreKey] ?: 0
        _teamScores[scoreKey] = currentScore + plusScore
    }

    fun updateCompletedTaskResult(task: CompletedTask, status: TaskResultStatus) {
        check(task.task.roundIndex == currentRoundIndex) {
            "Task can be updated only during the current playing round"
        }
        check(task.task.teamIndex == currentTeamIndex) {
            "Task can be updated only by the current playing team"
        }
        val taskIndex = _completedTasks.indexOf(task)
        _completedTasks[taskIndex] = task.copy(
            result = TaskResult(
                score = taskStatusToScore(status),
                status = status
            )
        )
    }

    fun getWinnerTeamIndex(): Int {
        if (!finished) {
            return -1;
        }
        return (0..settings.teamCount)
            .map { i -> i to getTeamTotalScore(i) }
            .maxByOrNull { it.second }?.first ?: 0
    }

    fun getTeamTotalScore(teamIndex: Int): Int {
        return _teamScores[teamIndex.toString()] ?: 0
    }

    fun getCurrentTeamRoundResult(): TeamRoundResult {
        return _completedTasks.run(this::convertToTeamResult)
    }

    private fun convertToTeamResult(tasks: List<CompletedTask>): TeamRoundResult {
        return TeamRoundResult(tasks)
    }

    private fun taskStatusToScore(status: TaskResultStatus): Int {
        return when (status) {
            DONE -> settings.pointsForDone
            FAILED -> settings.pointsForFail
            SKIPPED -> 0
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

    private fun nextWord(): Word {
        return try {
            dictionary.getRandomWord(_usedWords)
        } catch (ex: NoWordsFoundException) {
            //should never happen
            dictionary.getRandomWord(setOf())
        }
    }
}