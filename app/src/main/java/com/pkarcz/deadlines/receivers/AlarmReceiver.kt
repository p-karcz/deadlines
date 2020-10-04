package com.pkarcz.deadlines.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pkarcz.deadlines.DeadlinesMainActivity
import com.pkarcz.deadlines.R
import com.pkarcz.deadlines.database.Task
import com.pkarcz.deadlines.database.getInstance
import kotlinx.coroutines.*

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val taskId = intent!!.getIntExtra("TASK_ID", 1)

        val notificationIntent = Intent(context, DeadlinesMainActivity::class.java).let {
            PendingIntent.getActivity(context, taskId, it, 0)
        }

//        val tasksDatabase = getInstance(context!!)
//
//        val alarmJob = Job()
//        val alarmScope = CoroutineScope(Dispatchers.Main + alarmJob)
//
//        alarmScope.launch {
//            withContext(Dispatchers.IO) {
//                tasksDatabase.taskDao.changeProgress(taskId)
//            }
//        }

        val builder = NotificationCompat.Builder(context!!, "over")
            .setSmallIcon(R.drawable.ic_deadline_notification)
            .setContentTitle("Test")
            .setContentText("Tescik")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(notificationIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(taskId, builder.build())
        }
    }
}