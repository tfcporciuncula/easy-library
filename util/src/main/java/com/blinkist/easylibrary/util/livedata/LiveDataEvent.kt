package com.blinkist.easylibrary.util.livedata

import androidx.lifecycle.LiveData

/**
 * Wrapper for events emitted by a [LiveData].
 *
 * Inspired by https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
open class LiveDataEvent {

  private var hasNotBeenHandled = true

  fun doIfNotHandled(block: () -> Unit) {
    if (hasNotBeenHandled) {
      hasNotBeenHandled = false
      block()
    }
  }
}
