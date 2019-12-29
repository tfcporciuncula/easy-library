package com.blinkist.easylibrary.features.library

import androidx.recyclerview.widget.DiffUtil
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.databinding.DataBindingAdapter
import com.blinkist.easylibrary.model.presentation.Book

class LibraryAdapter(
  private val onItemClicked: (Book) -> Unit
) : DataBindingAdapter<LibraryItem>(DiffCallback()) {

  class DiffCallback : DiffUtil.ItemCallback<LibraryItem>() {

    override fun areItemsTheSame(oldItem: LibraryItem, newItem: LibraryItem) = when {
      oldItem is Book && newItem is Book -> oldItem.id == newItem.id
      else -> oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: LibraryItem, newItem: LibraryItem) = oldItem == newItem
  }

  override fun getItemViewType(position: Int) = when (getItem(position)) {
    is LibraryItem.Book -> R.layout.item_book
    is LibraryItem.Section -> R.layout.item_section
  }

  override fun onBindViewHolder(holder: ViewHolder<LibraryItem>, position: Int) {
    super.onBindViewHolder(holder, position)

    (getItem(position) as? Book)?.let { book ->
      holder.itemView.setOnClickListener { onItemClicked(book) }
    }
  }
}
