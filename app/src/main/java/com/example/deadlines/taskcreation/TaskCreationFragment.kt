package com.example.deadlines.taskcreation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.deadlines.R
import com.example.deadlines.databinding.FragmentTaskCreationBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TaskCreationFragment : Fragment() {

    lateinit var binding: FragmentTaskCreationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task_creation, container, false)

        val onTimeSetListenerImpl = OnTimeSetListenerImpl(binding.textEndTime)
        val onDateSetListenerImpl = OnDateSetListenerImpl(binding.textEndDate)

        binding.textEndTime.setOnClickListener {
            TimePickerFragment(onTimeSetListenerImpl).show(parentFragmentManager, "Pick Time")
        }

        binding.textEndDate.setOnClickListener {
            DatePickerFragment(onDateSetListenerImpl).show(parentFragmentManager, "Pick Date")
        }

        return binding.root
    }
}

class OnTimeSetListenerImpl(private val textView: TextView): TimePickerDialog.OnTimeSetListener {
    override fun onTimeSet(view: TimePicker, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val timeFormatted = SimpleDateFormat.getTimeInstance().format(calendar.time)
        textView.text = timeFormatted
    }
}

class OnDateSetListenerImpl(private val textView: TextView): DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val dateFormatted = SimpleDateFormat.getDateInstance().format(calendar.time)
        textView.text = dateFormatted
    }
}