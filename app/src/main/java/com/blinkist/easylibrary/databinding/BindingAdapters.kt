package com.blinkist.easylibrary.databinding

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.api.load

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
  view.load(imageUrl) { crossfade(true) }
}

@BindingAdapter("onRefresh")
fun setOnRefreshListener(view: SwipeRefreshLayout, listener: () -> Unit) {
  view.setOnRefreshListener { listener() }
}

@BindingAdapter("isVisible")
fun setVisibility(view: View, isVisible: Boolean) {
  view.isVisible = isVisible
}
