package com.blinkist.easylibrary.test

import com.blinkist.easylibrary.R
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MaterialComponentsRule : TestWatcher() {

  override fun starting(description: Description?) {
    super.starting(description)
    targetContext.setTheme(R.style.AppTheme)
  }
}
