package com.example.deadlines.alltasks

import androidx.lifecycle.ViewModel
import com.example.deadlines.database.TasksDatabase

class AllTasksViewModel(private val database: TasksDatabase) : ViewModel() {

    val tasks = database.taskDao.getAllTasks()
}