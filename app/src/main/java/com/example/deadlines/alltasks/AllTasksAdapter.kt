package com.example.deadlines.alltasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deadlines.R
import com.example.deadlines.database.Task
import kotlinx.android.synthetic.main.task_template.view.*

class AllTasksAdapter: ListAdapter<Task, AllTasksAdapter.AllTasksViewHolder>(TaskCallback()) {

    var tasks: List<Task>? = emptyList()

    class AllTasksViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTasksViewHolder {
        return AllTasksViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_template, parent, false))
    }

    override fun onBindViewHolder(holder: AllTasksViewHolder, position: Int) {
        holder.view.first_text_view.text = tasks?.get(position)?.name ?: ""
    }

    class TaskCallback: DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

    }

}