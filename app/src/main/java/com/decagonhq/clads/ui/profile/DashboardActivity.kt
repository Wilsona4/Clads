package com.decagonhq.clads.ui.profile

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.DashboardActivityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DashboardActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarDashboard.dashboardActivityToolbar)

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

        /*Close Drawer Icon*/
        drawerCloseIcon.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        /*Open Messages onClick*/
        toolbarNotificationIcon.setOnClickListener {
            navController.navigate(R.id.nav_messages)
        }
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
                        toolbarProfilePicture.visibility = View.VISIBLE
                        toolbarUserName.visibility = View.VISIBLE
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
                    R.id.addMeasurementFragment -> {
                        bottomNavigationView.visibility = View.GONE
                        toolbarProfilePicture.visibility = View.INVISIBLE
                        toolbarUserName.visibility = View.INVISIBLE
                        toolbarNotificationIcon.visibility = View.GONE
                        toolbarFragmentName.visibility = View.VISIBLE
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
