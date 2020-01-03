package com.blinkist.easylibrary.debug

import android.content.Context
import com.facebook.stetho.Stetho
import javax.inject.Inject

class StethoInitializer @Inject constructor(private val context: Context) {

  fun init() = Stetho.initializeWithDefaults(context)
}
