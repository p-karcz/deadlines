package com.example.deadlines.alltasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deadlines.database.Task
import com.example.deadlines.databinding.TaskTemplateBinding

class AllTasksAdapter: ListAdapter<Task, AllTasksAdapter.AllTasksViewHolder>(TaskCallback()) {

    class AllTasksViewHolder(private var binding: TaskTemplateBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.firstTextView.text = task.name
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTasksViewHolder {
        return AllTasksViewHolder(TaskTemplateBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AllTasksViewHolder, position: Int) {
        holder.bind(getItem(position))
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