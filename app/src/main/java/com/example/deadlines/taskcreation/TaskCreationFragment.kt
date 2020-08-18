package com.example.deadlines.taskcreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.deadlines.DeadlinesMainViewModel
import com.example.deadlines.OnDateSetListenerImpl
import com.example.deadlines.OnTimeSetListenerImpl
import com.example.deadlines.R
import com.example.deadlines.databinding.FragmentTaskCreationBinding

class TaskCreationFragment : Fragment() {

    lateinit var binding: FragmentTaskCreationBinding

    private val activityViewModel: DeadlinesMainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task_creation, container, false)

        val onTimeSetListenerImpl = OnTimeSetListenerImpl(binding.textEndTime, activityViewModel)
        val onDateSetListenerImpl = OnDateSetListenerImpl(binding.textEndDate, activityViewModel)

        binding.textEndTime.setOnClickListener {
            TimePickerFragment(onTimeSetListenerImpl).show(parentFragmentManager, "Pick Time")
        }

        binding.textEndDate.setOnClickListener {
            DatePickerFragment(onDateSetListenerImpl).show(parentFragmentManager, "Pick Date")
        }

        binding.taskName.addTextChangedListener {
            activityViewModel.fillName(it.toString())
        }

        binding.taskDescription.addTextChangedListener {
            activityViewModel.fillDescription(it.toString())
        }

        return binding.root
    }
}