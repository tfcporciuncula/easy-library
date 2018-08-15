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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import kotlinx.android.synthetic.main.item_book.view.*
import java.text.SimpleDateFormat
import javax.inject.Inject

class LibraryAdapter @Inject constructor() :
    ListAdapter<Librariable, LibraryAdapter.ViewHolder>(DiffCallback()) {

    companion object {
        private const val ITEM_VIEW_TYPE_BOOK = R.layout.item_book
        private const val ITEM_VIEW_TYPE_SECTION = R.layout.item_section
    }

    class DiffCallback : DiffUtil.ItemCallback<Librariable>() {

        override fun areItemsTheSame(oldItem: Librariable, newItem: Librariable) = when {
            oldItem is Book && newItem is Book -> oldItem.id == newItem.id
            else -> oldItem == newItem
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

                Glide.with(coverImageView)
                    .load(book.thumbnail)
                    .transition(withCrossFade())
                    .into(coverImageView)
            }
        }

        fun bindWeekSection(weekSection: WeekSection) {
            val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)
            val formattedInitialDate = dateFormat.format(weekSection.initialDate)
            val formattedFinalDate = dateFormat.format(weekSection.finalDate)

            val title = itemView.context.getString(
                R.string.week_section_title, formattedInitialDate, formattedFinalDate
            )
            (itemView as TextView).text = title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val librariable = getItem(position)
        when (librariable) {
            is Book -> holder.bindBook(librariable)
            is WeekSection -> holder.bindWeekSection(librariable)
        }
    }

    override fun getItemViewType(position: Int) = if (getItem(position) is Book) {
        ITEM_VIEW_TYPE_BOOK
    } else {
        ITEM_VIEW_TYPE_SECTION
    }
}
