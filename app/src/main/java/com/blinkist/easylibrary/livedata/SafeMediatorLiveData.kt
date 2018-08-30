package com.blinkist.easylibrary.livedata

import android.arch.lifecycle.MediatorLiveData

/**
 * A safer [MediatorLiveData] that doesn't accept nor exposes nullables.
 */
class SafeMediatorLiveData<T : Any>(initialValue: T) : MediatorLiveData<T>() {

    init {
        value = initialValue
    }

    override fun getValue(): T = super.getValue()!!
}
