package com.pkarcz.deadlines.alltasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pkarcz.deadlines.R
import com.pkarcz.deadlines.database.TasksDatabase
import com.pkarcz.deadlines.database.getInstance
import com.pkarcz.deadlines.databinding.FragmentAllTasksBinding

class AllTasksFragment : Fragment() {

    private lateinit var binding: FragmentAllTasksBinding

    private lateinit var database: TasksDatabase

    private lateinit var viewModel: AllTasksViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_all_tasks, container, false)

        database = getInstance(requireNotNull(this.activity).application)

        viewModel = ViewModelProvider(this, AllTasksViewModelFactory(database)).get(
            AllTasksViewModel::class.java)

        val allTasksAdapter = AllTasksAdapter()

        val swapCallback = SwapCallback()

        ItemTouchHelper(swapCallback).attachToRecyclerView(binding.tasksRecycler)

        allTasksAdapter.submitList(viewModel.tasks.value)

        val manager = LinearLayoutManager(this.context)

        binding.tasksRecycler.apply {
            layoutManager = manager
            adapter = allTasksAdapter
        }

        binding.viewModel = viewModel

        viewModel.tasks.observe(viewLifecycleOwner, {
            allTasksAdapter.submitList(it)
        })

        return binding.root
    }

    inner class SwapCallback: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.delete(viewHolder.absoluteAdapterPosition)
        }
    }
}