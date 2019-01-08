package com.blinkist.easylibrary.features.library

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.blinkist.easylibrary.test.LazyActivityTestRule
import com.blinkist.easylibrary.test.instrumentationContext
import com.google.android.material.snackbar.Snackbar
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
    RESTMockServerStarter.startSync(AndroidAssetsFileParser(instrumentationContext), AndroidLogger())
  }

  @After fun tearDown() = RESTMockServer.shutdown()

  @Test fun shouldShowSnackbarWhenServiceFails() {
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
