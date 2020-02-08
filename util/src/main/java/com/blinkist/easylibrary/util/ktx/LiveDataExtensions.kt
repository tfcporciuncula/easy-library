package com.blinkist.easylibrary.util.ktx

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.observe
import com.blinkist.easylibrary.util.livedata.LiveDataEvent

inline fun <T, A> LiveData<T>.select(
  crossinline property: T.() -> A
) = map(property).distinctUntilChanged()

inline fun <T : LiveDataEvent> LiveData<T?>.observeEvent(
  owner: LifecycleOwner,
  crossinline onEventNotHandled: (T) -> Unit
) = observe(owner) { event ->
  event?.let {
    event.doIfNotHandled { onEventNotHandled(event) }
  }
}
