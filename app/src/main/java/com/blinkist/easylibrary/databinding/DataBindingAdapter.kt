package com.blinkist.easylibrary.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blinkist.easylibrary.BR

abstract class DataBindingAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) :
  ListAdapter<T, DataBindingAdapter.ViewHolder<T>>(diffCallback) {

  class ViewHolder<T>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) {
      binding.setVariable(BR.item, item)
      binding.executePendingBindings()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) = holder.bind(getItem(position))

  abstract override fun getItemViewType(position: Int): Int
}
