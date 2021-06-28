package com.decagonhq.clads.ui.profile

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.DashboardActivityBinding
import com.decagonhq.clads.ui.profile.bottomnav.MessagesFragment
import com.decagonhq.clads.util.Constants
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.SessionManager
import com.decagonhq.clads.util.handleApiError
import com.decagonhq.clads.viewmodels.ImageUploadViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: DashboardActivityBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var listener: NavController.OnDestinationChangedListener
    private lateinit var editProfile: TextView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbarNotificationIcon: ImageView
    private lateinit var toolbarProfilePicture: ImageView
    private lateinit var toolbarUserName: TextView
    private lateinit var toolbarFragmentName: TextView
    private lateinit var drawerCloseIcon: ImageView
    private lateinit var imageUploadViewModel: ImageUploadViewModel
    lateinit var profileImage: ImageView
    private var selectedImage: Uri? = null

    @Inject
    lateinit var sessionManager: SessionManager


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DashboardActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarDashboard.dashboardActivityToolbar)
        val imageUploadViewModel = ViewModelProvider(this).get(ImageUploadViewModel::class.java)


        /*Set Status bar Color*/
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.statusBarColor = resources?.getColor(R.color.white)!!
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navViewHeader = navView.getHeaderView(0)
        editProfile = navViewHeader.findViewById(R.id.nav_drawer_edit_profile_text_view)
        drawerCloseIcon = navViewHeader.findViewById(R.id.nav_drawer_close_icon_image_view)
        profileImage = navViewHeader.findViewById(R.id.nav_drawer_profile_avatar_image_view)
        val imageUrl = sessionManager.loadFromSharedPref(Constants.IMAGE_URL)


        /*Initialize Toolbar Views*/
        toolbarNotificationIcon =
            binding.appBarDashboard.dashboardActivityToolbarNotificationImageView
        toolbarProfilePicture = binding.appBarDashboard.dashboardActivityToolbarProfileImageView
        toolbarUserName = binding.appBarDashboard.dashboardActivityToolbarHiIjeomaTextView
        toolbarFragmentName = binding.appBarDashboard.dashboardActivityToolbarFragmentNameTextView

        bottomNavigationView =
            binding.appBarDashboard.contentDashboard.dashboardActivityBottomNavigationView

        navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            navController.graph, drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        bottomNavigationView.setupWithNavController(navController)

        /*Set Up Navigation Change Listener*/
        onDestinationChangedListener()

        /*Edit User Detail onClick*/
        editProfile.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.editProfileFragment)
        }

        /*load profile image from shared pref*/
        Glide.with(this)
            .load(imageUrl)
            .into(profileImage)

        /*Close Drawer Icon*/
        drawerCloseIcon.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        /*Open Messages onClick*/
        toolbarNotificationIcon.setOnClickListener {
            navController.navigate(R.id.nav_messages)
            toolbarProfilePicture.visibility = View.INVISIBLE
            toolbarUserName.visibility = View.INVISIBLE
            toolbarNotificationIcon.visibility = View.GONE
        }
        imageUploadViewModel.getUserImage()
        imageUploadViewModel.userProfileImage.observe(this, Observer {
            if (it != null) {

                Glide.with(this)
                    .load(imageUrl)
                    .into(toolbarProfilePicture)

            } else {
                Toast.makeText(this, "NULLLL", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /*CLose Nav Drawer if open, on back press*/
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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

    /*Set Up Navigation Change Listener*/
    private fun onDestinationChangedListener() {
        listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                toolbarFragmentName.text = destination.label ?: getString(R.string.app_name)
                when (destination.id) {
                    R.id.editProfileFragment -> {
                        bottomNavigationView.visibility = View.GONE
                        toolbarProfilePicture.visibility = View.INVISIBLE
                        toolbarUserName.visibility = View.INVISIBLE
                        toolbarNotificationIcon.visibility = View.GONE
                        toolbarFragmentName.visibility = View.VISIBLE
                    }
                    R.id.clientFragment -> {
                        bottomNavigationView.visibility = View.VISIBLE
                        toolbarProfilePicture.visibility = View.INVISIBLE
                        toolbarUserName.visibility = View.INVISIBLE
                        toolbarNotificationIcon.visibility = View.GONE
                        toolbarFragmentName.visibility = View.GONE
                    }
                    R.id.addClientFragment -> {
                        bottomNavigationView.visibility = View.GONE
                        toolbarProfilePicture.visibility = View.INVISIBLE
                        toolbarUserName.visibility = View.INVISIBLE
                        toolbarNotificationIcon.visibility = View.GONE
                        toolbarFragmentName.visibility = View.VISIBLE
                    }
//                    R.id.addMeasurementFragment -> {
//                        bottomNavigationView.visibility = View.GONE
//                        toolbarProfilePicture.visibility = View.INVISIBLE
//                        toolbarUserName.visibility = View.INVISIBLE
//                        toolbarNotificationIcon.visibility = View.GONE
//                        toolbarFragmentName.visibility = View.VISIBLE
//                    }
                    R.id.nav_messages -> {
                        bottomNavigationView.visibility = View.VISIBLE
                        toolbarProfilePicture.visibility = View.INVISIBLE
                        toolbarUserName.visibility = View.INVISIBLE
                        toolbarNotificationIcon.visibility = View.GONE
                        toolbarFragmentName.visibility = View.VISIBLE
                    }
                    R.id.nav_media -> {
                        bottomNavigationView.visibility = View.VISIBLE
                        toolbarProfilePicture.visibility = View.GONE
                        toolbarUserName.visibility = View.GONE
                        toolbarNotificationIcon.visibility = View.GONE
                        toolbarFragmentName.visibility = View.VISIBLE
                    }
                    R.id.photoGalleryEditImageFragment -> {
                        bottomNavigationView.visibility = View.GONE
                        toolbarProfilePicture.visibility = View.GONE
                        toolbarUserName.visibility = View.GONE
                        toolbarNotificationIcon.visibility = View.GONE
                        toolbarFragmentName.visibility = View.INVISIBLE
                    }
                    R.id.mediaFragmentPhotoName -> {
                        bottomNavigationView.visibility = View.GONE
                        toolbarProfilePicture.visibility = View.GONE
                        toolbarUserName.visibility = View.GONE
                        toolbarNotificationIcon.visibility = View.GONE
                        toolbarFragmentName.visibility = View.INVISIBLE
                    }
                    else -> {
                        bottomNavigationView.visibility = View.VISIBLE
                        toolbarProfilePicture.visibility = View.VISIBLE
                        toolbarUserName.visibility = View.VISIBLE
                        toolbarNotificationIcon.visibility = View.VISIBLE
                        toolbarFragmentName.visibility = View.GONE
                    }
                }
            }
    }

}
