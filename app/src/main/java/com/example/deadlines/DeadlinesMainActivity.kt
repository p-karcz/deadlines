package com.example.deadlines

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.deadlines.database.TasksDatabase
import com.example.deadlines.database.getInstance
import com.example.deadlines.databinding.DeadlinesMainActivityBinding
import com.google.android.material.bottomappbar.BottomAppBar

class DeadlinesMainActivity : AppCompatActivity() {

    private lateinit var binding: DeadlinesMainActivityBinding

    private lateinit var tasksDatabase: TasksDatabase

    // Instance of a ViewModel which should handle most of the actions
    // Created using ktx
    private val viewModel: DeadlinesMainViewModel by viewModels {
        DeadlinesMainViewModelFactory(tasksDatabase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tasksDatabase = getInstance(this)

        binding = DataBindingUtil.setContentView(this, R.layout.deadlines_main_activity)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.allTasksFragment -> {
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.fab.show()
                    binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    binding.fab.setImageResource(R.drawable.ic_baseline_add_24)

                    binding.fab.setOnClickListener {
                        viewModel.taskBuilder.reset()
                        viewModel.resetCorrectness()
                        controller.navigate(R.id.action_allTasksFragment_to_taskCreationFragment)
                    }
                }
                R.id.taskCreationFragment -> {
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.fab.show()
                    binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    binding.fab.setImageResource(R.drawable.ic_baseline_save_24)

                    binding.fab.setOnClickListener {
                        viewModel.checkAndBuild()
                        controller.navigate(R.id.action_taskCreationFragment_to_allTasksFragment)
                    }
                }
                else -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.fab.hide()
                }
            }
        }

        // Assigning ViewModel created earlier to the viewModel variable inside <layout> tag in the activity's layout
        binding.viewModel = viewModel
    }
}