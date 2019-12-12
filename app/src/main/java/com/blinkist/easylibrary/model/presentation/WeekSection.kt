package com.blinkist.easylibrary.model.presentation

import com.blinkist.easylibrary.features.library.LibraryItem

data class WeekSection(
  val initialDate: String,
  val finalDate: String
) : LibraryItem.Section()
