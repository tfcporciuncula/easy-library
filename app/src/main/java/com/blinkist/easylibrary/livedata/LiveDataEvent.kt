package com.blinkist.easylibrary.livedata

import android.arch.lifecycle.LiveData

/**
 * Wrapper for events emitted by a [LiveData].
 *
 * Inspired by https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
open class LiveDataEvent<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled() = if (hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        content
    }
}
