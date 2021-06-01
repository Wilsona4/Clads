package com.decagonhq.clads.ui.view

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
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
        const val PASSWORD = "password"
    }

    @Test
    fun testFragmentVisibility(){
//        val fragmentFactory = FragmentFactory()
//        val bundle = Bundle()

        val scenario = launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_MaterialComponents)

        onView(withId(R.id.login_fragment_parent_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_welcome_back_message_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_google_sign_in_card_view)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_email_address_edit_text_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_email_address_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_password_edit_text_layout)).check(matches(isDisplayed()))

        onView(withId(R.id.login_fragment_password_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_log_in_card_view)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_log_in_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_new_user_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_sign_up_for_free_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.login_fragment_forget_password_text_view)).check(matches(isDisplayed()))

        onView(withId(R.id.login_fragment_email_address_edit_text)).perform(replaceText(EMAIL))
        onView(withId(R.id.login_fragment_password_edit_text)).perform(replaceText(PASSWORD))

        closeSoftKeyboard()
        onView(withId(R.id.login_fragment_log_in_card_view)).perform(swipeUp(), click())


    }




}