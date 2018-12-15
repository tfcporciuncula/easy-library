package com.blinkist.easylibrary.library

import androidx.recyclerview.widget.DiffUtil
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.databinding.DataBindingAdapter
import com.blinkist.easylibrary.model.Book
import javax.inject.Inject

class LibraryAdapter @Inject constructor() : DataBindingAdapter<Librariable>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Librariable>() {

        override fun areItemsTheSame(oldItem: Librariable, newItem: Librariable) = when {
            oldItem is Book && newItem is Book -> oldItem.id == newItem.id
            else -> oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Librariable, newItem: Librariable): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int) = if (getItem(position) is Book) {
        R.layout.item_book
    } else {
        R.layout.item_section
    }
}
