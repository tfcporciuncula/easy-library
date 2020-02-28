package com.blinkist.easylibrary.debug

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ScrollView
import com.blinkist.easylibrary.R

class DebugView : ScrollView {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
    context, attrs, defStyleAttr, defStyleRes
  )

  init {
    LayoutInflater.from(context).inflate(R.layout.debug_drawer, this, true)
  }
}
