package com.blinkist.easylibrary.test.ktx

import com.google.common.truth.Subject

inline fun <reified T> Subject.isInstanceOf() = isInstanceOf(T::class.java)
