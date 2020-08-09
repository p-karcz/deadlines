package com.example.deadlines.taskcreation

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import java.util.*

class TimePickerFragment(private val listener: TimePickerDialog.OnTimeSetListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(context, listener, hour, minute, DateFormat.is24HourFormat(activity))
    }
}