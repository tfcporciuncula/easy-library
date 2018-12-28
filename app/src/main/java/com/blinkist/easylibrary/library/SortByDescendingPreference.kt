package com.blinkist.easylibrary.library

import android.content.Context
import android.content.SharedPreferences
import com.blinkist.easylibrary.R
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SortByDescendingPreference @Inject constructor(
  private val context: Context,
  private val sharedPreferences: SharedPreferences
) {

  fun get() = sharedPreferences.getBoolean(context.getString(R.string.sort_order_key), true)

  fun set(value: Boolean) {
    sharedPreferences.edit().putBoolean(context.getString(R.string.sort_order_key), value).apply()
  }
}
