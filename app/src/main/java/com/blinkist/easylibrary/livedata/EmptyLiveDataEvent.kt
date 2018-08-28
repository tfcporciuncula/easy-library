package com.blinkist.easylibrary.livedata

/**
 * Just like [LiveDataEvent], but carries no content.
 */
abstract class EmptyLiveDataEvent : LiveDataEvent<Unit>(Unit) {

    inline fun doIfNotHandled(block: () -> Unit) = getContentIfNotHandled()?.let { block() }
}
