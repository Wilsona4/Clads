package com.decagonhq.clads.ui.authentication

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.decagonhq.clads.R
import com.decagonhq.clads.ui.authentication.EmailSignUpFragmentTest.Companion.EMAIL
import com.decagonhq.clads.ui.authentication.EmailSignUpFragmentTest.Companion.PASSWORD
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginFragmentTest {

    @Before
    fun setUp() {
        val scenario =
            launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_MaterialComponents)
    }

    // Test view visibility
    @Test
    fun login_fragment_parent_layout_visibility() {
        onView(withId(R.id.login_fragment_parent_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun login_fragment_email_address_edit_text() {
        onView(withId(R.id.login_fragment_email_address_edit_text)).check(matches(isDisplayed()))
    }

    @Test
    fun login_fragment_password_edit_text() {
        onView(withId(R.id.login_fragment_password_edit_text)).check(matches(isDisplayed()))
    }

    @Test
    fun login_fragment_log_in_card_view() {
        onView(withId(R.id.login_fragment_log_in_button)).check(matches(isDisplayed()))
    }

    // Test typing and perform click
    @Test
    fun test_typing() {
        val mockNavController = mock(NavController::class.java)

//        val scenario =
//            launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_MaterialComponents)
//        scenario.onFragment { fragment ->
//            Navigation.setViewNavController(fragment.requireView(), mockNavController)
//        }

        val scenario =
            launchFragmentInContainer(themeResId = R.style.Base_Theme_MaterialComponents) {
                LoginFragment().also { fragment ->

                    fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                        if (viewLifecycleOwner != null) {
                            // The fragment’s view has just been created
                            Navigation.setViewNavController(fragment.requireView(), mockNavController)
                        }
                    }
                }
            }

        onView(withId(R.id.login_fragment_email_address_edit_text)).perform(replaceText(EMAIL))
        onView(withId(R.id.login_fragment_password_edit_text)).perform(replaceText(PASSWORD))
        closeSoftKeyboard()
        onView(withId(R.id.login_fragment_log_in_button)).perform(click())
//        verify(mockNavController).navigate(R.id.action_loginFragment_to_dashboardFragment)
    }
}
