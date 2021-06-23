package com.decagonhq.clads.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.decagonhq.clads.R
import com.decagonhq.clads.ui.profile.DashboardActivity
import com.decagonhq.clads.util.Constants
import com.decagonhq.clads.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    @Inject
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)

        /*Move to the home page after showing splash screen for X milliseconds*/
        GlobalScope.launch {
            delay(1000L)
            withContext(Dispatchers.Main) {

                val pref = sessionManager.loadFromSharedPref(Constants.TOKEN)
                /*Use finish to disable the page when back button is pressed from homePage*/
                if (pref.isEmpty()) {
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else if (pref.isNotEmpty()) {
                    val intent =
                        Intent(this@SplashScreenActivity, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
