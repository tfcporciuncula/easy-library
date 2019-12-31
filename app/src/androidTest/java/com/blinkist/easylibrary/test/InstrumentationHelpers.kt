package com.blinkist.easylibrary.test

import android.app.Activity
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.blinkist.easylibrary.di.DaggerComponentProvider
import com.blinkist.easylibrary.di.TestComponent

class LazyActivityTestRule<T : Activity>(activityClass: Class<T>) : ActivityTestRule<T>(activityClass, false, false) {

  fun launchActivity(): T = launchActivity(null)
}

inline val instrumentationContext: Context get() = InstrumentationRegistry.getInstrumentation().context

inline val targetContext: Context get() = InstrumentationRegistry.getInstrumentation().targetContext

inline val testInjector
  get() = (targetContext.applicationContext as DaggerComponentProvider).component as TestComponent
