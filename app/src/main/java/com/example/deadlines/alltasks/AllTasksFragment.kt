package com.example.deadlines.alltasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deadlines.R
import com.example.deadlines.database.getInstance
import com.example.deadlines.databinding.FragmentAllTasksBinding
import com.google.android.material.bottomappbar.BottomAppBar

class AllTasksFragment : Fragment() {

    private lateinit var binding: FragmentAllTasksBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_all_tasks, container, false)

        val database = getInstance(requireNotNull(this.activity).applicationContext)

        val viewModel = ViewModelProvider(this, AllTasksViewModelFactory(database)).get(AllTasksViewModel::class.java)

        val allTasksAdapter = AllTasksAdapter()
        allTasksAdapter.submitList(viewModel.tasks.value)
        val manager = LinearLayoutManager(this.context)

        binding.tasksRecycler.apply {
            layoutManager = manager
            adapter = allTasksAdapter
        }

        binding.viewModel = viewModel

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            allTasksAdapter.submitList(it)
        })

        return binding.root
    }
}