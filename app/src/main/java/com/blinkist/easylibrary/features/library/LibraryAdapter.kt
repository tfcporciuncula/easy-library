package com.blinkist.easylibrary.features.library

import androidx.recyclerview.widget.DiffUtil
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.databinding.DataBindingAdapter
import com.blinkist.easylibrary.model.LocalBook
import javax.inject.Inject

class LibraryAdapter @Inject constructor() : DataBindingAdapter<LibraryItem>(DiffCallback()) {

  class DiffCallback : DiffUtil.ItemCallback<LibraryItem>() {

    override fun areItemsTheSame(oldItem: LibraryItem, newItem: LibraryItem) = when {
      oldItem is LocalBook && newItem is LocalBook -> oldItem.id == newItem.id
      else -> oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: LibraryItem, newItem: LibraryItem): Boolean {
      return oldItem == newItem
    }
  }

  override fun getItemViewType(position: Int) = when (getItem(position)) {
    is LibraryItem.Book -> R.layout.item_book
    is LibraryItem.Section -> R.layout.item_section
  }
}
