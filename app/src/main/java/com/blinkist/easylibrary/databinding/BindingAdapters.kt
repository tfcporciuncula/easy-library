package com.blinkist.easylibrary.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
  Glide.with(view)
    .load(imageUrl)
    .transition(DrawableTransitionOptions.withCrossFade())
    .into(view)
}

@BindingAdapter("onRefresh")
fun setOnRefreshListener(view: SwipeRefreshLayout, listener: () -> Unit) {
  view.setOnRefreshListener { listener() }
}
