package com.blinkist.easylibrary.features.library

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.test.instrumentation.LazyActivityTestRule
import com.blinkist.easylibrary.test.instrumentation.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.blinkist.easylibrary.test.instrumentation.atPosition
import com.blinkist.easylibrary.test.instrumentation.isRefreshing
import com.google.android.material.snackbar.Snackbar
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers.pathContains
import java.util.concurrent.TimeUnit

class LibraryPageObject(private val activityRule: LazyActivityTestRule<LibraryActivity>) {

  private val recyclerView get() = onView(withId(R.id.recyclerView))
  private val snackbar get() = onView(isAssignableFrom(Snackbar.SnackbarLayout::class.java))
  private val progressBar get() = onView(withId(R.id.swipeRefreshLayout))
  private val sortMenu get() = onView(withId(R.id.menu_sort))
  private val ascendingOption get() = onView(withId(R.id.ascendingTextView))
  private val descendingOption get() = onView(withId(R.id.descendingTextView))
  private val ascendingCheck get() = onView(withId(R.id.ascendingCheckImageView))
  private val descendingCheck get() = onView(withId(R.id.descendingCheckImageView))

  val defaultBooksAmount = 22

  fun launch() = activityRule.launchActivity()

  fun pullToRefresh() {
    recyclerView.perform(swipeDown())
  }

  fun scrollToTop() {
    recyclerView.perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
  }

  fun clickOnSortMenu() {
    sortMenu.perform(click())
  }

  fun clickOnAscending() {
    ascendingOption.perform(click())
  }

  fun clickOnDescending() {
    descendingOption.perform(click())
  }

  fun assertSortOptionsDialogIsVisible() {
    ascendingOption.check(matches(isDisplayed()))
    descendingOption.check(matches(isDisplayed()))
  }

  fun assertSortOptionDialogIsNotVisible() {
    ascendingOption.check(doesNotExist())
    descendingOption.check(doesNotExist())
  }

  fun assertAscendingIsSelected() {
    ascendingCheck.check(matches(isDisplayed()))
  }

  fun assertDescendingIsSelected() {
    descendingCheck.check(matches(isDisplayed()))
  }

  fun assertSnackbarIsVisible() {
    snackbar.check(matches(isDisplayed()))
  }

  fun assertSnackbarIsNotVisible() {
    snackbar.check(doesNotExist())
  }

  fun assertProgressBarIsVisible() {
    progressBar.check(matches(isRefreshing()))
  }

  fun assertListItemCount(expectedCount: Int) {
    recyclerView.check(withItemCount(expectedCount))
  }

  fun assertFirstBookIsMostRecent() {
    recyclerView.check(matches(atPosition(1, hasDescendant(withText("Refactoring")))))
  }

  fun assertFirstBookIsLeastRecent() {
    recyclerView.check(matches(atPosition(1, hasDescendant(withText("Design Patterns")))))
  }

  fun givenServiceFails() {
    RESTMockServer.whenGET(pathContains("books")).thenReturnEmpty(500)
  }

  fun givenServiceReturnsDefaultBooks() {
    RESTMockServer.whenGET(pathContains("books")).thenReturnFile(200, "books.json")
  }

  fun givenServiceReturnsDefaultBooksAndOneMoreBookOnRefresh() {
    RESTMockServer.whenGET(pathContains("books"))
      .thenReturnFile(200, "books.json")
      .thenReturnFile(200, "onemorebook.json")
  }

  fun givenServiceReturnsDefaultBooksAndFailsOnRefresh() {
    RESTMockServer.whenGET(pathContains("books"))
      .thenReturnFile(200, "books.json")
      .thenReturnEmpty(500)
  }

  fun givenServiceReturnsDefaultBooksAndDelaysOnRefresh() {
    RESTMockServer.whenGET(pathContains("books"))
      .thenReturnFile(200, "books.json")
      .thenReturnFile(200, "books.json")
      .delayBody(TimeUnit.SECONDS, 0, 5)
  }
}
