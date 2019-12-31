package com.blinkist.easylibrary.test

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class CoroutinesRule<T : CoroutineDispatcher>(
  val dispatcher: T
) : TestWatcher() {

  companion object {
    operator fun invoke() = CoroutinesRule(Dispatchers.Unconfined)
  }

  override fun starting(description: Description?) {
    super.starting(description)
    Dispatchers.setMain(dispatcher)
  }

  override fun finished(description: Description?) {
    super.finished(description)
    Dispatchers.resetMain()
    (dispatcher as? TestCoroutineDispatcher)?.cleanupTestCoroutines()
  }
}
