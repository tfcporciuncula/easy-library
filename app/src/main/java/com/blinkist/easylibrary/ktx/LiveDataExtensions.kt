package com.blinkist.easylibrary.ktx

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map

inline fun <T, A> LiveData<T>.select(
  crossinline property: T.() -> A
) = map(property).distinctUntilChanged()

inline fun <T, A, B> LiveData<T>.select(
  crossinline property1: T.() -> A,
  crossinline property2: T.() -> B
) = map { Pair(property1(it), property2(it)) }.distinctUntilChanged()

inline fun <T, A, B, C> LiveData<T>.select(
  crossinline property1: T.() -> A,
  crossinline property2: T.() -> B,
  crossinline property3: T.() -> C
) = map { Triple(property1(it), property2(it), property3(it)) }.distinctUntilChanged()
