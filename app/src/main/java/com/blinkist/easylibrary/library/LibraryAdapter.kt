package com.blinkist.easylibrary.library

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.model.Book
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_library.view.*

class LibraryAdapter : ListAdapter<Book, LibraryAdapter.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Book>() {

        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(book: Book) {
            itemView.apply {
                titleTextView.text = book.title
                authorsTextView.text = book.authors
                publishedDateTextView.text = book.publishedDate

                Glide.with(coverImageView).load(book.thumbnail).into(coverImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_library, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
