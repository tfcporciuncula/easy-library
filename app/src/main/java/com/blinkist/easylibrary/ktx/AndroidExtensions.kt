package com.blinkist.easylibrary.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(@StringRes resId: Int) = Snackbar.make(this, resId, Snackbar.LENGTH_LONG).show()

inline fun <reified T : Activity> Context.newIntent(block: Intent.() -> Unit = {}) =
  Intent(this, T::class.java).apply(block)
