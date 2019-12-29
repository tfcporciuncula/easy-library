package com.blinkist.easylibrary.test

import android.app.Activity
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule

class LazyActivityTestRule<T : Activity>(activityClass: Class<T>) : ActivityTestRule<T>(activityClass, false, false) {

  fun launchActivity(): T = launchActivity(null)
}

val instrumentationContext: Context get() = InstrumentationRegistry.getInstrumentation().context
