package com.blinkist.easylibrary.model

import com.blinkist.easylibrary.features.library.LibraryItem

data class WeekSection(
  val initialDate: String,
  val finalDate: String
) : LibraryItem.Section()
