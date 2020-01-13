package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.debug.StethoInitializer
import com.blinkist.easylibrary.features.library.LibraryViewModel

interface ApplicationProvisions {
  val stethoInitializer: StethoInitializer
}

interface ViewModelProvisions {
  val libraryViewModel: LibraryViewModel
}
