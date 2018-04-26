package com.blinkist.easylibrary.library

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.model.WeekSection
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_book.view.*

class LibraryAdapter : ListAdapter<Librariable, LibraryAdapter.ViewHolder>(DiffCallback()) {

    companion object {
        private const val ITEM_VIEW_TYPE_BOOK = 0
        private const val ITEM_VIEW_TYPE_WEEK_SECTION = 1
    }

    class DiffCallback : DiffUtil.ItemCallback<Librariable>() {

        override fun areItemsTheSame(oldItem: Librariable, newItem: Librariable) = when {
            oldItem is Book && newItem is Book -> oldItem.id == newItem.id
            oldItem is WeekSection && newItem is WeekSection -> oldItem.title == newItem.title
            else -> false
        }

        override fun areContentsTheSame(oldItem: Librariable, newItem: Librariable): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindBook(book: Book) {
            itemView.apply {
                titleTextView.text = book.title
                authorsTextView.text = book.authors
                publishedDateTextView.text = book.publishedDate

                Glide.with(coverImageView).load(book.thumbnail).into(coverImageView)
            }
        }

        fun bindWeekSection(weekSection: WeekSection) {
            (itemView as TextView).text = weekSection.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutResId = if (viewType == ITEM_VIEW_TYPE_BOOK) {
            R.layout.item_book
        } else {
            R.layout.item_section
        }

        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val librariable = getItem(position)
        when (librariable) {
            is Book -> holder.bindBook(librariable)
            is WeekSection -> holder.bindWeekSection(librariable)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is Book) {
            ITEM_VIEW_TYPE_BOOK
        } else {
            ITEM_VIEW_TYPE_WEEK_SECTION
        }
    }
}
