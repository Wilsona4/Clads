package com.decagonhq.clads.ui.profile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.DashboardActivityBinding
import com.decagonhq.clads.ui.profile.bottomnav.MessagesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class DashboardActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: DashboardActivityBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var listener: NavController.OnDestinationChangedListener
    private lateinit var editProfile: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DashboardActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarDashboard.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navViewHeader = navView.getHeaderView(0)
        editProfile = navViewHeader.findViewById(R.id.nav_drawer_edit_profile_text_view)

        bottomNavigationView = binding.appBarDashboard.contentDashboard.dashboardActivityBottomNavigationView
        navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            navController.graph, drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        bottomNavigationView.setupWithNavController(navController)

        // var notificationButton = binding.
        /*Edit User Detail onClick*/
//        editProfile.setOnClickListener {
//            navController.navigate(R.id.action_nav_home_to_editProfileFragment)
//        }
        /*Set Up Navigation Change Listener*/
        listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                if (destination.id == R.id.editProfileFragment) {
//                    "Implement Change in Action Bar"
//                    bottomNavigationView.visibility = View.GONE
                } else {
//                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        createNotification()
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    private fun createNotification() {
        // Create the NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            // create an explicit intent to open the second activity
            val notifyIntent = Intent(this, MessagesFragment::class.java).apply {
                putExtra("firstName", "Ola")
                putExtra("lastName", "Michavelli")
                putExtra("body", getString(R.string.lorem_ipsum))
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            // get the pending intent
            val notifyPendingIntent =
                PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val builder = NotificationCompat.Builder(this, "ID")
                .setContentTitle("My notification")
                .setSmallIcon(R.drawable.clads_logo_blue)
                .setContentText("You have a notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Set the intent that will fire when the user taps the notification
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true)

            // set on click listener to button1 to open this assignment
//            notify_me.setOnClickListener {
//                notificationManager.notify(100, builder.build())
//            }
        }
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
