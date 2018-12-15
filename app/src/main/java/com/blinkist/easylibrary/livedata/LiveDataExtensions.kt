package com.blinkist.easylibrary.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * A simpler [LiveData.observe] method that abstracts away null checking.
 */
inline fun <T> LiveData<T>.observe(owner: LifecycleOwner, crossinline observer: (t: T) -> Unit) {
    observe(owner, Observer { it?.let(observer) })
}
