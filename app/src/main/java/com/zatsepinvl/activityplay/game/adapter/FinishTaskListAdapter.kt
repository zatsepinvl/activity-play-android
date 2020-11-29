package com.zatsepinvl.activityplay.game.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.android.onClick
import com.zatsepinvl.activityplay.core.model.TaskResultStatus.*
import com.zatsepinvl.activityplay.core.model.TeamRoundResult
import com.zatsepinvl.activityplay.databinding.ViewFinishTaskListItemBinding
import com.zatsepinvl.activityplay.game.viewmodel.GameViewModel
import com.zatsepinvl.activityplay.resource.ResourceService
import com.zatsepinvl.activityplay.team.model.Team

class FinishTaskListAdapter(
    private val gameViewModel: GameViewModel,
    private val resourceService: ResourceService
) : RecyclerView.Adapter<FinishTaskListAdapter.TaskListViewHolder>() {

    class TaskListViewHolder(val binding: ViewFinishTaskListItemBinding, view: View) :
        RecyclerView.ViewHolder(view)

    private lateinit var teamRoundResult: TeamRoundResult
    private val team: Team

    init {
        team = gameViewModel.currentTeam.value!!
        updateTasks()
    }

    private fun updateTasks() {
        teamRoundResult = gameViewModel.game.getCurrentTeamRoundResult()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_finish_task_list_item, parent, false)
        val binding = ViewFinishTaskListItemBinding.bind(view)
        view.tag = binding
        return TaskListViewHolder(binding, view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val completedTask = teamRoundResult.tasks[position]
        holder.binding.apply {
            team = this@FinishTaskListAdapter.team
            task = completedTask
            score = completedTask.result.score
            iconDrawable = when (completedTask.result.status) {
                DONE -> R.drawable.ic_sentiment_very_satisfied
                SKIPPED -> R.drawable.ic_sentiment_neutral
                FAILED -> R.drawable.ic_sentiment_very_dissatisfied
            }
            iconTint = when (completedTask.result.status) {
                DONE -> this@FinishTaskListAdapter.team.color
                SKIPPED -> resourceService.colorCode(R.color.md_grey_400)
                FAILED -> resourceService.colorCode(R.color.md_grey_400)
            }
            root.onClick {
                val newStatus = when (completedTask.result.status) {
                    DONE -> SKIPPED
                    SKIPPED -> FAILED
                    FAILED -> DONE
                }
                gameViewModel.updateTask(completedTask, newStatus)
                updateTasks()
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount() = teamRoundResult.tasks.size
}

