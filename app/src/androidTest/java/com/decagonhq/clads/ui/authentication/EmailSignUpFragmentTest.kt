package com.decagonhq.clads.ui.authentication

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4ClassRunner::class)
class EmailSignUpFragmentTest {

    @Before
    fun setUp() {
        val scenario =
            launchFragmentInContainer<EmailSignUpFragment>(themeResId = R.style.Base_Theme_MaterialComponents)
    }

    @Test
    fun test_email_fragment_title_text_view_in_view() {
        onView(withId(R.id.email_sign_up_fragment_title_text_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_email_fragment_first_name_text_view_in_view() {
        onView(withId(R.id.email_sign_up_fragment_first_name_edit_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_email_fragment_account_category_dropdown_in_view() {
        onView(withId(R.id.email_sign_up_fragment_account_category_text_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_email_fragment_account_sign_up_button_in_view() {
        swipeUp()
        onView(withId(R.id.email_sign_up_fragment_signup_button)).check(matches(isDisplayed()))
    }

    /*Test Navigation to Profile Activity*/
    @Test
    fun test_email_signup_fragment_navigation_to_email_confirmation_fragment() {
        // Create a TestNavHostController
        val navController = mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer(themeResId = R.style.Base_Theme_MaterialComponents) {
                EmailSignUpFragment().also { fragment ->

                    fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                        if (viewLifecycleOwner != null) {
                            // The fragment’s view has just been created
                            Navigation.setViewNavController(fragment.requireView(), navController)
                        }
                    }
                }
            }

        /*Input Data*/
        onView(withId(R.id.email_sign_up_fragment_first_name_edit_text)).perform(
            replaceText(
                FIRST_NAME
            )
        )
        closeSoftKeyboard()
        onView(withId(R.id.email_sign_up_fragment_last_name_edit_text)).perform(
            replaceText(
                LAST_NAME
            )
        )
        closeSoftKeyboard()
        onView(withId(R.id.email_sign_up_fragment_email_edit_text)).perform(replaceText(EMAIL))
        closeSoftKeyboard()
        /*Select Spinner Item*/
        onView(withId(R.id.email_sign_up_fragment_account_category_text_view)).perform(
            replaceText(
                ACCOUNT_TYPE
            )
        )
        onView(withId(R.id.email_sign_up_fragment_password_edit_text)).perform(
            swipeUp(),
            replaceText(PASSWORD)
        )
        closeSoftKeyboard()
        onView(withId(R.id.email_sign_up_fragment_confirm_password_edit_text)).perform(
            swipeUp(),
            replaceText(PASSWORD)
        )
        closeSoftKeyboard()
        Thread.sleep(250)
        /* Verify that performing a click changes the NavController’s state*/
        swipeUp()
        onView(withId(R.id.email_sign_up_fragment_signup_button)).perform(click())
        verify(navController).navigate(R.id.email_confirmation_fragment)
    }

    companion object {
        const val FIRST_NAME = "John"
        const val LAST_NAME = "Doe"
        const val EMAIL = "johndoe@gmail.com"
        const val PASSWORD = "Abcde@1234"
        const val ACCOUNT_TYPE = "Tailor"
    }
}
