package com.blinkist.easylibrary.features.library

import android.content.Context
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.doOnLayout
import com.blinkist.easylibrary.R

object ThemePopup {

  fun show(context: Context, anchor: View, onItemClicked: (Int) -> Unit, onDismissed: () -> Unit) =
    PopupMenu(context, anchor).apply {
      inflate(R.menu.theme_menu)
      setOnMenuItemClickListener { onItemClicked(it.itemId); true }
      setOnDismissListener { onDismissed() }
      anchor.doOnLayout { show() }
    }
}
