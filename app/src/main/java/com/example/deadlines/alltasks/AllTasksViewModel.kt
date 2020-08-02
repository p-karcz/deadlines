package com.example.deadlines.alltasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deadlines.database.TasksDatabase

class AllTasksViewModel(private val database: TasksDatabase) : ViewModel() {

    val tasks = database.taskDao.getAllTasks()

    private var _navigateToTaskCreation = MutableLiveData<Boolean>()
    val navigateToTaskCreation: LiveData<Boolean>
        get() = _navigateToTaskCreation

    fun startNavigationToTaskCreation() {
        _navigateToTaskCreation.value = true
    }

    fun doneNavigationToTaskCreation() {
        _navigateToTaskCreation.value = null
    }
}