package com.example.deadlines

import androidx.lifecycle.*
import com.example.deadlines.database.Task
import com.example.deadlines.database.TasksDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeadlinesMainViewModel(private val tasksDatabase: TasksDatabase): ViewModel() {
    private var nameCorrectness: Boolean = false
    private var descriptionCorrectness: Boolean = false
    var timeCorrectness: Boolean = false
    var dateCorrectness: Boolean = false

    val taskBuilder = Task.TaskBuilder()

    private suspend fun insertTask(task: Task) {
        withContext(Dispatchers.IO) {
            tasksDatabase.taskDao.insertTasks(task)
        }
    }

    fun fillName(name: String?) {
        if(!name.isNullOrEmpty()) {
            nameCorrectness = true
            taskBuilder.name(name)
        }
    }

    fun fillDescription(description: String?) {
        if(!description.isNullOrEmpty()) {
            descriptionCorrectness = true
            taskBuilder.description(description)
        }
    }

    fun checkAndBuild() {
        if(nameCorrectness && descriptionCorrectness && timeCorrectness && dateCorrectness) {
            viewModelScope.launch {
                insertTask((taskBuilder.build()))
            }
        }
    }

    fun resetCorrectness() {
        nameCorrectness = false
        descriptionCorrectness = false
        timeCorrectness = false
        dateCorrectness = false
    }
}