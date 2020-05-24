package com.zatsepinvl.activityplay.game.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.core.model.CompletedTask
import com.zatsepinvl.activityplay.databinding.ViewFinishTaskListItemBinding
import com.zatsepinvl.activityplay.team.model.Team

class FinishTaskListAdapter(
    private val team: Team,
    private val tasks: List<CompletedTask>
) : RecyclerView.Adapter<FinishTaskListAdapter.TaskListViewHolder>() {

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
        holder.dataBinding.apply {
            team = this@FinishTaskListAdapter.team
            task = tasks[position]
        }
    }

    override fun getItemCount() = tasks.size
}

