package com.decagonhq.clads.ui.authentication

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.decagonhq.clads.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4ClassRunner::class)
class SignUpOptionsFragmentTest {

    @Before
    fun setUp() {
        val scenario =
            launchFragmentInContainer<SignUpOptionsFragment>(themeResId = R.style.Base_Theme_MaterialComponents)
    }

    @Test
    fun test_sign_up_options_clads_logo_in_view() {
        onView(withId(R.id.sign_up_options_fragment_clads_logo_image_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_sign_up_options_fragment_clads_sign_up_with_google_button_in_view() {
        onView(withId(R.id.sign_up_options_fragment_clads_sign_up_with_google_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_sign_up_options_fragment_login_text_view_in_view() {
        onView(withId(R.id.sign_up_options_fragment_login_text_view))
            .check(matches(isDisplayed()))
    }

    /*Test Navigation to Profile Activity*/
    @Test
    fun test_signup_options_navigation_to_email_signup_fragment() {
        // Create a TestNavHostController
        val navController = mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer(themeResId = R.style.Base_Theme_MaterialComponents) {
                SignUpOptionsFragment().also { fragment ->
                    fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                        if (viewLifecycleOwner != null) {
                            // The fragment’s view has just been created
                            Navigation.setViewNavController(fragment.requireView(), navController)
                        }
                    }
                }
            }

        /* Verify that performing a click changes the NavController’s state*/
        onView(withId(R.id.sign_up_options_fragment_sign_up_with_email_button)).perform(click())
        verify(navController).navigate(R.id.email_sign_up_fragment)
    }
}
