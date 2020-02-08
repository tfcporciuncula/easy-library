package com.blinkist.easylibrary.features.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.databinding.BookItemBinding
import com.blinkist.easylibrary.databinding.SectionItemBinding
import com.blinkist.easylibrary.model.presentation.Book
import com.blinkist.easylibrary.model.presentation.WeekSection
import com.blinkist.easylibrary.util.ktx.loadWithCrossFade

class LibraryAdapter(
  private val onBookClicked: (Book) -> Unit
) : ListAdapter<LibraryItem, LibraryAdapter.ViewHolder>(DiffCallback()) {

  class DiffCallback : DiffUtil.ItemCallback<LibraryItem>() {

    override fun areItemsTheSame(oldItem: LibraryItem, newItem: LibraryItem) = when {
      oldItem is Book && newItem is Book -> oldItem.id == newItem.id
      else -> oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: LibraryItem, newItem: LibraryItem) = oldItem == newItem
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(libraryItem: LibraryItem, onBookClicked: (Book) -> Unit) {
      when (libraryItem) {
        is Book -> bindBook(libraryItem, onBookClicked)
        is WeekSection -> bindWeekSection(libraryItem)
      }
    }

    private fun bindBook(book: Book, onBookClicked: (Book) -> Unit) {
      with(BookItemBinding.bind(itemView)) {
        coverImageView.loadWithCrossFade(book.imageUrl)
        titleTextView.text = book.title
        authorsTextView.text = book.authors
        publishedDateTextView.text = book.publishedDateText
        root.setOnClickListener { onBookClicked(book) }
      }
    }

    private fun bindWeekSection(weekSection: WeekSection) {
      with(SectionItemBinding.bind(itemView)) {
        titleTextView.text =
          itemView.context.getString(R.string.week_section_title, weekSection.initialDate, weekSection.finalDate)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) =
    holder.bind(getItem(position), onBookClicked)

  override fun getItemViewType(position: Int) = when (getItem(position)) {
    is LibraryItem.Book -> R.layout.book_item
    is LibraryItem.Section -> R.layout.section_item
  }
}
