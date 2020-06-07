package com.zatsepinvl.activityplay.game.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.android.onClick
import com.zatsepinvl.activityplay.core.model.TaskResultStatus.DONE
import com.zatsepinvl.activityplay.core.model.TaskResultStatus.SKIPPED
import com.zatsepinvl.activityplay.core.model.TeamRoundResult
import com.zatsepinvl.activityplay.databinding.ViewFinishTaskListItemBinding
import com.zatsepinvl.activityplay.game.viewmodel.PlayRoundViewModel
import com.zatsepinvl.activityplay.team.model.Team

class FinishTaskListAdapter(
    private val viewModel: PlayRoundViewModel
) : RecyclerView.Adapter<FinishTaskListAdapter.TaskListViewHolder>() {

    private lateinit var teamRoundResult: TeamRoundResult
    private val team: Team

    init {
        team = viewModel.currentTeam
        updateTasks()
    }

    private fun updateTasks() {
        teamRoundResult = viewModel.game.getCurrentTeamRoundResult()
    }

    class TaskListViewHolder(
        val dataBinding: ViewFinishTaskListItemBinding,
        view: View
    ) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_finish_task_list_item, parent, false)
        val dataBinding = ViewFinishTaskListItemBinding.bind(view)
        view.tag = dataBinding
        return TaskListViewHolder(dataBinding, view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val completedTask = teamRoundResult.tasks[position]
        holder.dataBinding.apply {
            team = this@FinishTaskListAdapter.team
            task = completedTask
            score = completedTask.result.score
            root.onClick {
                val newStatus = when (completedTask.result.status) {
                    DONE -> SKIPPED
                    SKIPPED -> DONE
                }
                viewModel.updateTask(completedTask, newStatus)
                updateTasks()
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount() = teamRoundResult.tasks.size
}

