package com.blinkist.easylibrary.util.ktx

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

fun ImageView.loadWithCrossFade(url: String) = Glide.with(this).load(url).transition(withCrossFade()).into(this)
