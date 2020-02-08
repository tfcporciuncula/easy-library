@file:Suppress("UNCHECKED_CAST")

package com.blinkist.easylibrary.util.ktx

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> FragmentActivity.lazyViewModel(
  crossinline provider: () -> T
) = viewModels<T> {
  object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = provider() as T
  }
}

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
  crossinline provider: () -> T
) = viewModels<T> {
  object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = provider() as T
  }
}

inline fun <reified T : ViewModel> Fragment.lazyActivityViewModel(
  crossinline provider: () -> T
) = activityViewModels<T> {
  object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = provider() as T
  }
}
