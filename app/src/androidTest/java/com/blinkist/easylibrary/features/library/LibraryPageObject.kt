package com.blinkist.easylibrary.features.library

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.test.LazyActivityTestRule
import com.blinkist.easylibrary.test.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.blinkist.easylibrary.test.atPosition
import com.google.android.material.snackbar.Snackbar
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers.pathContains

class LibraryPageObject(private val activityRule: LazyActivityTestRule<LibraryActivity>) {

  private val recyclerView get() = onView(withId(R.id.recyclerView))
  private val snackbar get() = onView(isAssignableFrom(Snackbar.SnackbarLayout::class.java))
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

  fun assertSortOptionsAreVisible() {
    ascendingOption.check(matches(isDisplayed()))
    descendingOption.check(matches(isDisplayed()))
  }

  fun assertSortOptionDialogIsNotPresent() {
    ascendingOption.check(doesNotExist())
    descendingOption.check(doesNotExist())
  }

  fun assertAscendingSortOptionIsSelected() {
    ascendingCheck.check(matches(isDisplayed()))
  }

  fun assertDescendingSortOptionIsSelected() {
    descendingCheck.check(matches(isDisplayed()))
  }

  fun assertSnackbarIsVisible() {
    snackbar.check(matches(isDisplayed()))
  }

  fun assertSnackbarIsNotPresent() {
    snackbar.check(doesNotExist())
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
    RESTMockServer.whenGET(pathContains("books")).thenReturnFile(500, "error")
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
      .thenReturnFile(500, "error")
  }
}
