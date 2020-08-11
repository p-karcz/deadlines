package com.example.deadlines

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import com.example.deadlines.taskcreation.TaskCreationViewModel
import java.text.SimpleDateFormat
import java.util.*

enum class CurrFragment {
    ALL_TASKS,
    TASK_CREATION
}

class OnTimeSetListenerImpl(private val textView: TextView, private val viewModel: DeadlinesMainViewModel): TimePickerDialog.OnTimeSetListener {
    override fun onTimeSet(view: TimePicker, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val timeFormatted = SimpleDateFormat.getTimeInstance().format(calendar.time)
        textView.text = timeFormatted
    }
}

class OnDateSetListenerImpl(private val textView: TextView, private val viewModel: DeadlinesMainViewModel): DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val dateFormatted = SimpleDateFormat.getDateInstance().format(calendar.time)
        textView.text = dateFormatted
    }
}