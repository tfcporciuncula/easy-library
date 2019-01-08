package com.blinkist.easylibrary.ktx

fun <T> unsynchronizedLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)
