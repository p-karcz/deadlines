package com.example.deadlines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.example.deadlines.databinding.DeadlinesMainActivityBinding
import com.google.android.material.bottomappbar.BottomAppBar

class DeadlinesMainActivity : AppCompatActivity() {

    private val viewModel: DeadlinesMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<DeadlinesMainActivityBinding>(this, R.layout.deadlines_main_activity)

        binding.viewModel = viewModel

        viewModel.navigateToFragment.observe(this) {
            if(it) {
                if(viewModel.curr == CurrFragment.ALL_TASKS) {
                    findNavController(R.id.navHostFragment).navigate(R.id.action_allTasksFragment_to_taskCreationFragment)
                    viewModel.curr = CurrFragment.TASK_CREATION
                    binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    binding.fab.setImageDrawable(getDrawable(R.drawable.ic_baseline_save_24))
                    viewModel.doneNavigating()
                } else {
                    findNavController(R.id.navHostFragment).navigate(R.id.action_taskCreationFragment_to_allTasksFragment)
                    viewModel.curr = CurrFragment.ALL_TASKS
                    binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    binding.fab.setImageDrawable(getDrawable(R.drawable.ic_baseline_add_24))
                    viewModel.doneNavigating()
                }
            }
        }
    }
}