package com.blinkist.easylibrary.ktx

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.observe
import com.blinkist.easylibrary.livedata.LiveDataEvent

inline fun <T, A> LiveData<T>.select(
  crossinline property: T.() -> A
) = map(property).distinctUntilChanged()

inline fun <E, T : LiveDataEvent<E>> LiveData<T?>.observeEvent(
  owner: LifecycleOwner,
  crossinline onEventNotHandled: (E) -> Unit
) = observe(owner) { event ->
  event?.let {
    it.doIfNotHandled { content -> onEventNotHandled(content) }
  }
}
