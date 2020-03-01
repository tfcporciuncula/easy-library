package com.blinkist.easylibrary.test.instrumentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers

class RecyclerViewItemCountAssertion private constructor(private val matcher: Matcher<Int>) : ViewAssertion {

  companion object {
    fun withItemCount(matcher: Matcher<Int>) = RecyclerViewItemCountAssertion(matcher)
    fun withItemCount(expectedCount: Int) = withItemCount(Matchers.`is`(expectedCount))
  }

  override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
    noViewFoundException?.let { throw it }
    ViewMatchers.assertThat((view as RecyclerView).adapter!!.itemCount, matcher)
  }
}

fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> =
  object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
    override fun describeTo(description: Description) {
      description.appendText("has item at position $position: ")
      itemMatcher.describeTo(description)
    }

    override fun matchesSafely(view: RecyclerView): Boolean {
      val viewHolder = view.findViewHolderForAdapterPosition(position) ?: return false
      return itemMatcher.matches(viewHolder.itemView)
    }
  }

fun isRefreshing(): Matcher<View> =
  object : BoundedMatcher<View, SwipeRefreshLayout>(SwipeRefreshLayout::class.java) {
    override fun describeTo(description: Description) {
      description.appendText("is refreshing")
    }

    override fun matchesSafely(view: SwipeRefreshLayout) = view.isRefreshing
  }
