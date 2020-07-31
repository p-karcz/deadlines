package com.example.deadlines.alltasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deadlines.R
import com.example.deadlines.database.Task
import com.example.deadlines.database.TasksDatabase
import com.example.deadlines.database.getInstance
import com.example.deadlines.databinding.AllTasksFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllTasksFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: AllTasksFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.all_tasks_fragment, container, false)

        val database = getInstance(requireNotNull(this.activity).applicationContext)

        val viewModel = ViewModelProvider(this, AllTasksViewModelFactory(database)).get(AllTasksViewModel::class.java)

        val allTasksAdapter = AllTasksAdapter()
        allTasksAdapter.tasks = viewModel.tasks.value
        val manager = LinearLayoutManager(this.context)

        binding.tasksRecycler.apply {
            layoutManager = manager
            adapter = allTasksAdapter
        }

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            allTasksAdapter.tasks = it
            allTasksAdapter.notifyDataSetChanged()
        })

        return binding.root
    }
}