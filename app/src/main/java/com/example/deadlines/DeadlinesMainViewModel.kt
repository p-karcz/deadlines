package com.example.deadlines

import androidx.lifecycle.*
import com.example.deadlines.database.Task
import com.example.deadlines.database.TasksDatabase
import kotlinx.coroutines.launch

class DeadlinesMainViewModel(private val tasksDatabase: TasksDatabase): ViewModel() {
    var curr = CurrFragment.ALL_TASKS

    fun addTaskToDatabase(task: Task) {
        viewModelScope.launch {
            tasksDatabase.taskDao.insertTasks(task)
        }
    }
}