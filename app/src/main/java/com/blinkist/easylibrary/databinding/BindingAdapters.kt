package com.blinkist.easylibrary.databinding

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.api.load

@BindingAdapter("imageUrl")
fun ImageView.loadImage(imageUrl: String) {
  load(imageUrl) { crossfade(true) }
}

@BindingAdapter("onRefresh")
fun SwipeRefreshLayout.setOnRefreshListener(listener: () -> Unit) {
  setOnRefreshListener { listener() }
}

@BindingAdapter("isVisible")
fun View.setVisibility(isVisible: Boolean) {
  this.isVisible = isVisible
}
