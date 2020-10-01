package com.pkarcz.deadlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pkarcz.deadlines.database.TasksDatabase
import java.lang.IllegalArgumentException

class DeadlinesMainViewModelFactory(
    private val database: TasksDatabase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DeadlinesMainViewModel::class.java)) {
            return DeadlinesMainViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}