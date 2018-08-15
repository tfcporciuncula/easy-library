package com.blinkist.easylibrary.library

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Lazy
import javax.inject.Inject

class LibraryViewModelFactory @Inject constructor(
    private val libraryViewModel: Lazy<LibraryViewModel>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = libraryViewModel.get() as T
}
