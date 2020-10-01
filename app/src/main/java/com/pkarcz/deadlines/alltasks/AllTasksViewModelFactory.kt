package com.pkarcz.deadlines.alltasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pkarcz.deadlines.database.TasksDatabase
import java.lang.IllegalArgumentException

class AllTasksViewModelFactory(
    private val database: TasksDatabase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AllTasksViewModel::class.java)) {
            return AllTasksViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}