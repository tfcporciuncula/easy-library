package com.blinkist.easylibrary.ktx

fun <T> unsyncLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)
