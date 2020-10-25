package com.pkarcz.deadlines

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.*
import com.pkarcz.deadlines.database.Task
import com.pkarcz.deadlines.database.TasksDatabase
import com.pkarcz.deadlines.receivers.AlarmReceiver
import kotlinx.coroutines.*

class DeadlinesMainViewModel(private val tasksDatabase: TasksDatabase): ViewModel() {
    private var nameCorrectness: Boolean = false
    private var descriptionCorrectness: Boolean = false
    var timeCorrectness: Boolean = false
    var dateCorrectness: Boolean = false

    private val _unfinishedTasks = tasksDatabase.taskDao.getUnfinishedTasks()
    val unfinishedTasks: LiveData<List<Task>>
        get() = _unfinishedTasks

    val taskBuilder = Task.TaskBuilder()

    private var alarmManager: AlarmManager? = null

    fun isUnfinishedTasksListEmpty(): Boolean {
        return _unfinishedTasks.value.isNullOrEmpty()
    }

    fun deleteFirstUnfinishedTask() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                tasksDatabase.taskDao.deleteTask(_unfinishedTasks.value!![0])
            }
        }
    }

    private suspend fun insertTask(task: Task): Int {
        var id = 0
        withContext(Dispatchers.IO) {
            id = tasksDatabase.taskDao.insertTask(task).toInt()
        }
        return id
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

    fun checkAndBuild(context: Context) {
        if(nameCorrectness && descriptionCorrectness && timeCorrectness && dateCorrectness) {
            viewModelScope.launch {
                val task = taskBuilder.build()
                val id = insertTask(task)

                if(alarmManager == null) {
                    alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                }

                val intent = Intent(context, AlarmReceiver::class.java).apply {
                    putExtra("TASK_ID", id)
                }

                val alarmIntent = PendingIntent.getBroadcast(context, id, intent, 0)

                alarmManager!!.setExact(AlarmManager.RTC_WAKEUP, task.time, alarmIntent)
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