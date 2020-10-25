package com.pkarcz.deadlines.alltasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkarcz.deadlines.database.TasksDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllTasksViewModel(private val database: TasksDatabase) : ViewModel() {

    val tasks = database.taskDao.getTasksInProgress()

    fun delete(position: Int) {
        viewModelScope.launch {
            deleteInBackground(position)
        }
    }

    private suspend fun deleteInBackground(position: Int) {
        withContext(Dispatchers.IO) {
            database.taskDao.deleteTask(tasks.value!![position])
        }
    }
}