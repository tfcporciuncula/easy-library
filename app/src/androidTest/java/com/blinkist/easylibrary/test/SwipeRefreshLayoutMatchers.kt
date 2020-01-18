package com.blinkist.easylibrary.test

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun isRefreshing(): Matcher<View> =
  object : BoundedMatcher<View, SwipeRefreshLayout>(SwipeRefreshLayout::class.java) {
    override fun describeTo(description: Description) {
      description.appendText("is refreshing")
    }

    override fun matchesSafely(view: SwipeRefreshLayout) = view.isRefreshing
  }
