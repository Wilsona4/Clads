package com.decagonhq.clads.ui.authentication

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.decagonhq.clads.R
import com.decagonhq.clads.ui.authentication.EmailSignUpFragmentTest.Companion.PASSWORD
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4ClassRunner::class)
class ResetPasswordFragmentTest {

    @Before
    fun setUp() {
        val scenario =
            launchFragmentInContainer<ResetPasswordFragment>(themeResId = R.style.Base_Theme_MaterialComponents)
        val context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun test_reset_password_fragment_back_arrow_in_view() {
        onView(withId(R.id.reset_password_fragment_back_arrow_image_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_reset_password_fragment_title_text_view_in_view() {
        onView(withId(R.id.reset_password_fragment_forgot_password_text_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_reset_password_fragment_new_password_edit_text_in_view() {
        onView(withId(R.id.reset_password_fragment_new_password_edit_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_reset_password_fragment_confirm_new_password_edit_text_in_view() {
        onView(withId(R.id.reset_password_fragment_confirm_new_password_edit_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_reset_password_fragment_reset_password_button_in_view() {
        onView(withId(R.id.reset_password_fragment_btn_reset_password_button)).perform(swipeUp())
            .check(matches(isDisplayed()))
    }

    /*Test Navigation to Login*/
    @Test
    fun test_reset_password_fragment_navigation_to_login_fragment() {

        // Create a TestNavHostController
        val navController = mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer(themeResId = R.style.Base_Theme_MaterialComponents) {
                ResetPasswordFragment().also { fragment ->
                    fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                        if (viewLifecycleOwner != null) {
                            // The fragment’s view has just been created
                            // navController.setGraph(R.navigation.nav_graph)
                            Navigation.setViewNavController(fragment.requireView(), navController)
                        }
                    }
                }
            }

        // disable animation for this to work
        onView(withId(R.id.reset_password_fragment_new_password_edit_text)).perform(
            replaceText(PASSWORD), closeSoftKeyboard()
        )

        // disable animation for this to work
        // closeSoftKeyboard()
        onView(withId(R.id.reset_password_fragment_confirm_new_password_edit_text)).perform(
            replaceText(PASSWORD), closeSoftKeyboard()
        )

        // closeSoftKeyboard()
        onView(withId(R.id.reset_password_fragment_btn_reset_password_button)).perform(
            click()
        )

        /*Check if Confirmation Password Reset Fragment is Displayed*/
        verify(navController).navigate(R.id.login_fragment)
    }
}
