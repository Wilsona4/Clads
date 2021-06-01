package com.decagonhq.clads.ui.view

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.decagonhq.clads.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginFragmentTest{

    companion object{
        const val EMAIL = "femiogundipe@gmail.com"
    }

    @Test
    fun testFragmentVisibility(){
        val fragmentFactory = FragmentFactory()
        val bundle = Bundle()

        val scenario = launchFragmentInContainer<LoginFragment>()

        onView(withId(R.id.to_sign_in_option)).check(matches(isDisplayed()))
    }



}