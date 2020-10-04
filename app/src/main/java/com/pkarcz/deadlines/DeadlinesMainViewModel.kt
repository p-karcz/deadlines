package com.pkarcz.deadlines

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.*
import com.pkarcz.deadlines.database.Task
import com.pkarcz.deadlines.database.TasksDatabase
import com.pkarcz.deadlines.receivers.AlarmReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeadlinesMainViewModel(private val tasksDatabase: TasksDatabase): ViewModel() {
    private var nameCorrectness: Boolean = false
    private var descriptionCorrectness: Boolean = false
    var timeCorrectness: Boolean = false
    var dateCorrectness: Boolean = false

    private val unfinishedTasks = tasksDatabase.taskDao.getUnfinishedTasks()

    val taskBuilder = Task.TaskBuilder()

    private var alarmManager: AlarmManager? = null

    fun isUnfinishedTasksListEmpty(): Boolean {
        return unfinishedTasks.value.isNullOrEmpty()
    }

    fun deleteFirstUnfinishedTask() {
        tasksDatabase.taskDao.deleteTasks(unfinishedTasks.value!![0])
    }

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

    fun checkAndBuild(context: Context) {
        if(nameCorrectness && descriptionCorrectness && timeCorrectness && dateCorrectness) {
            viewModelScope.launch {
                val task = taskBuilder.build()
                insertTask(task)

                if(alarmManager == null) {
                    alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                }

                val intent = Intent(context, AlarmReceiver::class.java).apply {
                    putExtra("TASK_ID", task.id)
                }

                val alarmIntent = PendingIntent.getBroadcast(context, task.id, intent, 0)

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