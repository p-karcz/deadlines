package com.example.deadlines.taskcreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.deadlines.R
import com.example.deadlines.databinding.FragmentTaskCreationBinding

class TaskCreationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTaskCreationBinding>(inflater, R.layout.fragment_task_creation, container, false)

        return binding.root
    }
}