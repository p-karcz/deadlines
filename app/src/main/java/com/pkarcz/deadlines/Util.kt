package com.pkarcz.deadlines

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import com.facebook.AccessToken
import java.text.SimpleDateFormat
import java.util.*

class OnTimeSetListenerImpl(private val textView: TextView, private val viewModel: DeadlinesMainViewModel): TimePickerDialog.OnTimeSetListener {
    override fun onTimeSet(view: TimePicker, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = 0
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val timeFormatted = SimpleDateFormat.getTimeInstance().format(calendar.time)
        textView.text = timeFormatted

        viewModel.taskBuilder.time(calendar.timeInMillis)
        viewModel.timeCorrectness = true
    }
}

class OnDateSetListenerImpl(private val textView: TextView, private val viewModel: DeadlinesMainViewModel): DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = 0
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val dateFormatted = SimpleDateFormat.getDateInstance().format(calendar.time)
        textView.text = dateFormatted

        viewModel.taskBuilder.time(calendar.timeInMillis)
        viewModel.dateCorrectness = true
    }
}

fun isUserLoggedIn(): Boolean {
    val accessToken = AccessToken.getCurrentAccessToken()
    return accessToken != null && !accessToken.isExpired
}