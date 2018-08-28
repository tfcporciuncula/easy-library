package com.blinkist.easylibrary.databinding

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.blinkist.easylibrary.BR

class DataBindingViewHolder<T>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}
