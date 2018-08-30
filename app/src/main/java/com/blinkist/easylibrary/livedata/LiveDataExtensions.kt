package com.blinkist.easylibrary.livedata

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData

/**
 * A simpler [LiveData.observe] method that abstracts away null checking.
 */
inline fun <T> LiveData<T>.observe(owner: LifecycleOwner, crossinline observer: (t: T) -> Unit) {
    observe(owner, android.arch.lifecycle.Observer { it?.let(observer) })
}
