package com.decagonhq.clads.ui.authentication

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.decagonhq.clads.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ResetPasswordConfirmationFragmentTest {
    @Before
    fun setUp() {
        val scenario =
            launchFragmentInContainer<ResetPasswordConfirmationFragment>(themeResId = R.style.Base_Theme_MaterialComponents)
        val context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun test_confirm_password_reset_fragment_button_view_in_view() {
        onView(withId(R.id.password_reset_confirmation_fragment_reset_password_button))
            .check(matches(isDisplayed()))
    }
}
