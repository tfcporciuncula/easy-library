package com.blinkist.easylibrary.library

import android.support.v7.util.DiffUtil
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.base.DataBindingAdapter
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
