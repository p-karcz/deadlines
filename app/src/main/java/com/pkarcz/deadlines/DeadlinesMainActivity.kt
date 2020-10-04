package com.pkarcz.deadlines

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.facebook.*
import com.facebook.internal.CallbackManagerImpl
import com.facebook.share.Sharer
import com.facebook.share.model.ShareContent
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.google.android.material.bottomappbar.BottomAppBar
import com.pkarcz.deadlines.bottomsheet.BottomSheetFragment
import com.pkarcz.deadlines.database.TasksDatabase
import com.pkarcz.deadlines.database.getInstance
import com.pkarcz.deadlines.databinding.DeadlinesMainActivityBinding


class DeadlinesMainActivity : AppCompatActivity() {

    private lateinit var binding: DeadlinesMainActivityBinding

    private lateinit var tasksDatabase: TasksDatabase

    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var callbackManager: CallbackManager

    private lateinit var shareDialog: ShareDialog

    private lateinit var shareContent: ShareLinkContent

    // Instance of a ViewModel which should handle most of the actions
    // Created using ktx
    private val viewModel: DeadlinesMainViewModel by viewModels {
        DeadlinesMainViewModelFactory(tasksDatabase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callbackManager = CallbackManager.Factory.create()

        createNotificationChannel("over")

        tasksDatabase = getInstance(this)

        val shareDialog = ShareDialog(this)
        val shareContent = ShareLinkContent.Builder()
            .setContentUrl(Uri.parse("https://github.com/p-karcz/deadlines"))
            .setQuote("Check out this application and manage your time more effectively")
            .build()

        shareDialog.registerCallback(callbackManager, object: FacebookCallback<Sharer.Result> {
            override fun onSuccess(result: Sharer.Result?) {
                viewModel.deleteFirstUnfinishedTask()
            }

            override fun onCancel() {
                shareDialog.show(shareContent)
            }

            override fun onError(error: FacebookException?) {

            }
        })

        showShareDialog()

        binding = DataBindingUtil.setContentView(this, R.layout.deadlines_main_activity)

        if(isUserLoggedIn()) {
            binding.navViewTop.menu.findItem(R.id.loginFragment).title = "Log out"
        } else {
            binding.navViewTop.menu.findItem(R.id.loginFragment).title = "Log in with Facebook"
        }

        val tokenTracker = object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(
                oldAccessToken: AccessToken?,
                currentAccessToken: AccessToken?
            ) {
                if (currentAccessToken == null) {
                    binding.navViewTop.menu.findItem(R.id.loginFragment).title = "Log in With Facebook"
                } else {
                    binding.navViewTop.menu.findItem(R.id.loginFragment).title = "Log out"
                }
            }
        }
        tokenTracker.startTracking()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.allTasksFragment, R.id.loginFragment, R.id.aboutFragment), binding.drawer
        )

        setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navViewTop, navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.allTasksFragment -> {
                    binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    binding.fab.setImageResource(R.drawable.ic_baseline_add_24)
                    binding.fab.setOnClickListener {
                        viewModel.taskBuilder.reset()
                        viewModel.resetCorrectness()
                        controller.navigate(R.id.action_allTasksFragment_to_taskCreationFragment)
                    }
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.toolbar.visibility = View.GONE
                    binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    binding.fab.show()
                }
                R.id.taskCreationFragment -> {
                    binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    binding.fab.setImageResource(R.drawable.ic_baseline_save_24)
                    binding.fab.setOnClickListener {
                        viewModel.checkAndBuild(this)
                        controller.navigate(R.id.action_taskCreationFragment_to_allTasksFragment)
                    }
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.toolbar.visibility = View.GONE
                    binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    binding.fab.show()
                }
                else -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.toolbar.visibility = View.VISIBLE
                    binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    binding.fab.hide()
                }
            }
        }

        binding.bottomAppBar.setNavigationOnClickListener {
            BottomSheetFragment().showNow(supportFragmentManager, "BottomSheetFragment")
        }

        binding.bottomAppBar.fabAnimationMode = BottomAppBar.FAB_ANIMATION_MODE_SLIDE

        // Assigning ViewModel created earlier to the viewModel variable inside <layout> tag in the activity's layout
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        showShareDialog()
    }

    private fun createNotificationChannel(channelId: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Deadline notification"
            val descriptionText = "Notification that appears when you don't meet a deadline"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    private fun showShareDialog() {
        if(!viewModel.isUnfinishedTasksListEmpty()){
            shareDialog.show(shareContent)
        }
    }
}