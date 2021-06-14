package com.decagonhq.clads.ui.profile.editprofile

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.decagonhq.clads.R
import org.junit.Before
import org.junit.Test

class SpecialtyModelFragmentTest {

    @Before
    fun setUp() {
        val scenario =
            launchFragmentInContainer<SpecialtyFragment>(themeResId = R.style.Base_Theme_MaterialComponents_Light)
    }

    // check if save button view is in place
    @Test
    fun test_specialty_fragment_layout_view_is_displayed_in_view() {
        Espresso.onView(ViewMatchers.withId(R.id.specialty_fragment_nested_scroll_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
