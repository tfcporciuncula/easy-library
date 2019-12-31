package com.blinkist.easylibrary.features.library

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.blinkist.easylibrary.test.LazyActivityTestRule
import com.blinkist.easylibrary.test.RestMockRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LibraryActivityTest {

  @get:Rule var restMockRule = RestMockRule()
  @get:Rule var activityRule = LazyActivityTestRule(LibraryActivity::class.java)

  private lateinit var libraryActivity: LibraryPageObject

  @Before fun setup() {
    libraryActivity = LibraryPageObject(activityRule)
  }

  @Test fun shouldShowSnackbarWhenServiceFailsOnLaunch() = with(libraryActivity) {
    givenServiceFails()
    launch()
    assertSnackbarIsVisible()
  }

  @Test fun shouldNotShowSnackbarWhenThereIsNoErrorOnLaunch() = with(libraryActivity) {
    givenServiceReturnsDefaultBooks()
    launch()
    assertSnackbarIsNotPresent()
  }

  @Test fun shouldReloadDataOnPullToRefresh() = with(libraryActivity) {
    givenServiceReturnsDefaultBooksAndOneMoreBookOnRefresh()
    launch()
    assertListItemCount(defaultBooksAmount)
    pullToRefresh()
    assertListItemCount(defaultBooksAmount + 1)
  }

  @Test fun shouldShowSnackbarAndKeepBooksWhenServiceFailsOnRefresh() = with(libraryActivity) {
    givenServiceReturnsDefaultBooksAndFailsOnRefresh()
    launch()
    assertListItemCount(defaultBooksAmount)
    pullToRefresh()
    assertListItemCount(defaultBooksAmount)
    assertSnackbarIsVisible()
  }

  @Test fun shouldShowSortBottomSheetDialogWhenSortMenuIsClicked() = with(libraryActivity) {
    givenServiceReturnsDefaultBooks()
    launch()
    clickOnSortMenu()
    assertSortOptionsAreVisible()
  }

  @Test fun shouldDismissDialogWhenClickingOnIt() = with(libraryActivity) {
    givenServiceReturnsDefaultBooks()
    launch()
    clickOnSortMenu()
    clickOnDescending()
    assertSortOptionDialogIsNotPresent()
    clickOnSortMenu()
    clickOnAscending()
    assertSortOptionDialogIsNotPresent()
  }

  @Test fun shouldHaveDescendingOrderSelectedAsDefault() = with(libraryActivity) {
    givenServiceReturnsDefaultBooks()
    launch()
    assertFirstBookIsMostRecent()
    clickOnSortMenu()
    assertDescendingSortOptionIsSelected()
  }

  @Test fun shouldSelectLatestClickedSortOption() = with(libraryActivity) {
    givenServiceReturnsDefaultBooks()
    launch()
    clickOnSortMenu()
    clickOnAscending()
    clickOnSortMenu()
    assertAscendingSortOptionIsSelected()
    clickOnDescending()
    clickOnSortMenu()
    assertDescendingSortOptionIsSelected()
  }

  @Test fun shouldReorderListWhenSortOrderChanges() = with(libraryActivity) {
    givenServiceReturnsDefaultBooks()
    launch()
    clickOnSortMenu()
    clickOnAscending()
    scrollToTop()
    assertFirstBookIsLeastRecent()
  }

  @Test fun shouldDismissDialogAndNotReorderListWhenSameSortOrderIsSelected() = with(libraryActivity) {
    givenServiceReturnsDefaultBooks()
    launch()
    clickOnSortMenu()
    assertDescendingSortOptionIsSelected()
    clickOnDescending()
    assertFirstBookIsMostRecent()
    assertSortOptionDialogIsNotPresent()
  }
}
