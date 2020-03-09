@file:Suppress("UNCHECKED_CAST")

package com.blinkist.easylibrary.util.ktx

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> FragmentActivity.lazyViewModel(
  crossinline viewModelProducer: () -> T
) = viewModels<T> {
  object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = viewModelProducer() as T
  }
}

inline fun <reified T : ViewModel> FragmentActivity.lazySavedStateViewModel(
  crossinline viewModelProducer: (handle: SavedStateHandle) -> T
) = viewModels<T> {
  object : AbstractSavedStateViewModelFactory(this, intent.extras) {
    override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle) =
      viewModelProducer(handle) as T
  }
}

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
  crossinline viewModelProducer: () -> T
) = viewModels<T> {
  object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = viewModelProducer() as T
  }
}

inline fun <reified T : ViewModel> Fragment.lazySavedStateViewModel(
  crossinline viewModelProducer: (handle: SavedStateHandle) -> T
) = viewModels<T> {
  object : AbstractSavedStateViewModelFactory(this, requireArguments()) {
    override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle) =
      viewModelProducer(handle) as T
  }
}

inline fun <reified T : ViewModel> Fragment.lazyActivityViewModel(
  crossinline viewModelProducer: () -> T
) = activityViewModels<T> {
  object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = viewModelProducer() as T
  }
}

inline fun <reified T : ViewModel> Fragment.lazySavedStateActivityViewModel(
  crossinline viewModelProducer: (handle: SavedStateHandle) -> T
) = activityViewModels<T> {
  object : AbstractSavedStateViewModelFactory(this, requireArguments()) {
    override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle) =
      viewModelProducer(handle) as T
  }
}
