package com.blinkist.easylibrary.library

import android.support.design.widget.Snackbar
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers.pathContains
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LibraryActivityErrorTest {

    @get:Rule var activityRule = ActivityTestRule<LibraryActivity>(LibraryActivity::class.java, false, false)

    @Before fun setup() {
        RESTMockServer.reset()
        RESTMockServer.whenGET(pathContains("books"))
            .thenReturnFile(500, "error")
        activityRule.launchActivity(null)
    }

    @Test fun shouldShowSnackbarWhenLibraryServiceFails() {
        onView(isAssignableFrom(Snackbar.SnackbarLayout::class.java)).check(matches(isDisplayed()))
    }
}

@RunWith(AndroidJUnit4::class)
class LibraryActivitySuccessTest {

    @get:Rule var activityRule = ActivityTestRule<LibraryActivity>(LibraryActivity::class.java, false, false)

    @Before fun setup() {
        RESTMockServer.reset()
        RESTMockServer.whenGET(pathContains("books"))
            .thenReturnFile(200, "books.json")
        activityRule.launchActivity(null)
    }

    @Test fun shouldNotShowSnackbarWhenThereIsNoError() {
        onView(isAssignableFrom(Snackbar.SnackbarLayout::class.java)).check(doesNotExist())
    }
}
