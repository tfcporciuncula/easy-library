package com.blinkist.easylibrary.util.ktx

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <T> unsyncLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

inline fun CoroutineScope.launchCatching(
  noinline block: suspend CoroutineScope.() -> Unit,
  crossinline onFailure: (Throwable) -> Unit
) {
  launch(CoroutineExceptionHandler { _, throwable -> onFailure(throwable) }, block = block)
}
