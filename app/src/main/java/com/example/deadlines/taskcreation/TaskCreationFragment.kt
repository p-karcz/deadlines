package com.example.deadlines.taskcreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.deadlines.OnDateSetListenerImpl
import com.example.deadlines.OnTimeSetListenerImpl
import com.example.deadlines.R
import com.example.deadlines.databinding.FragmentTaskCreationBinding

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