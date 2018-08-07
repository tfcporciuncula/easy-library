package com.blinkist.easylibrary.library

import android.support.design.widget.Snackbar
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.runner.AndroidJUnit4
import com.blinkist.easylibrary.test.LazyActivityTestRule
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger
import io.appflate.restmock.utils.RequestMatchers.pathContains
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LibraryActivityTest {

    @get:Rule var activityRule = LazyActivityTestRule(LibraryActivity::class.java)

    @Before fun setup() {
        RESTMockServerStarter.startSync(AndroidAssetsFileParser(InstrumentationRegistry.getContext()), AndroidLogger())
    }

    @After fun tearDown() = RESTMockServer.shutdown()

    @Test fun shouldShowSnackbarWhenLibraryServiceFails() {
        RESTMockServer.whenGET(pathContains("books")).thenReturnFile(500, "error")
        activityRule.launchActivity()

        onView(isAssignableFrom(Snackbar.SnackbarLayout::class.java)).check(matches(isDisplayed()))
    }

    @Test fun shouldNotShowSnackbarWhenThereIsNoError() {
        RESTMockServer.whenGET(pathContains("books")).thenReturnFile(200, "books.json")
        activityRule.launchActivity()

        onView(isAssignableFrom(Snackbar.SnackbarLayout::class.java)).check(doesNotExist())
    }
}
