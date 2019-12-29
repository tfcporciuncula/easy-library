package com.blinkist.easylibrary.livedata

import androidx.lifecycle.MutableLiveData

/**
 * A safer [MutableLiveData] that has an initial value and doesn't accept nor expose nullables.
 */
class NonNullMutableLiveData<T>(initialValue: T) : MutableLiveData<T>(initialValue) {

  override fun getValue(): T = super.getValue()!!

  inline fun update(block: T.() -> T) {
    value = block(value)
  }
}
