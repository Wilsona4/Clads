package com.decagonhq.clads.ui.authentication

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.decagonhq.clads.R
import org.junit.Before
import org.junit.Test

class EmailConfirmationFragmentTest {
    @Before
    fun setUp() {
        val scenario =
            launchFragmentInContainer<EmailConfirmationFragment>(themeResId = R.style.Base_Theme_MaterialComponents)
        val context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun test_email_confirmation_password_reset_fragment_button_view_in_view() {
        Espresso.onView(ViewMatchers.withId(R.id.email_confirmation_fragment_verify_email_address_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
