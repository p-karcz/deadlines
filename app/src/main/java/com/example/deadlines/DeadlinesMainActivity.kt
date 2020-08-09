package com.example.deadlines

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.example.deadlines.databinding.DeadlinesMainActivityBinding
import com.google.android.material.bottomappbar.BottomAppBar

class DeadlinesMainActivity : AppCompatActivity() {

    // Instance of a ViewModel which should handle most of the actions
    // Created using ktx
    private val viewModel: DeadlinesMainViewModel by viewModels()

    private lateinit var binding: DeadlinesMainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.deadlines_main_activity)

        // Assigning ViewModel created earlier to the viewModel variable inside <layout> tag in the activity's layout
        binding.viewModel = viewModel

        // Handle navigation using LiveData initialised in the ViewModel
        viewModel.navigateToFragment.observe(this) {

            // Check whether or not the LiveData is true
            // LiveData gets a true value after fab is pressed
            // False is assign later in this block, after performing navigation
            if(it) {
                // Check where to navigate
                // viewmodel.curr is a current fragment that is displayed
                if(viewModel.curr == CurrFragment.ALL_TASKS) {

                    // Navigate to the other fragment
                    findNavController(R.id.navHostFragment).navigate(R.id.action_allTasksFragment_to_taskCreationFragment)

                    // Change the current fragment
                    viewModel.curr = CurrFragment.TASK_CREATION

                    // Change fab to be displayed at the end of the bottom app bar
                    binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END

                    // Change fab icon
                    binding.fab.setImageDrawable(getDrawable(R.drawable.ic_baseline_save_24))

                    // Let ViewModel know that the navigation is completed
                    viewModel.doneNavigating()
                } else {

                    // Navigate to the other fragment
                    findNavController(R.id.navHostFragment).navigate(R.id.action_taskCreationFragment_to_allTasksFragment)

                    // Change the current fragment
                    viewModel.curr = CurrFragment.ALL_TASKS

                    // Change fab to be displayed at the center of the bottom app bar
                    binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

                    // Change fab icon
                    binding.fab.setImageDrawable(getDrawable(R.drawable.ic_baseline_add_24))

                    // Let ViewModel know that the navigation is completed
                    viewModel.doneNavigating()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if(viewModel.curr == CurrFragment.TASK_CREATION) {
            // Change the current fragment
            viewModel.curr = CurrFragment.ALL_TASKS

            // Change fab to be displayed at the center of the bottom app bar
            binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

            // Change fab icon
            binding.fab.setImageDrawable(getDrawable(R.drawable.ic_baseline_add_24))
        }
    }

    fun switch() {

    }
}