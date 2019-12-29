package com.blinkist.easylibrary.features.library

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.databinding.BottomSheetSortOptionsBinding
import com.blinkist.easylibrary.databinding.inflateBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LibrarySortOptionDialog : BottomSheetDialogFragment() {

  companion object {
    private val TAG: String = LibrarySortOptionDialog::class.java.name

    fun newInstance() = LibrarySortOptionDialog()
  }

  private val viewModel by activityViewModels<LibraryViewModel>()

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState)

    val binding = inflateBinding<BottomSheetSortOptionsBinding>(R.layout.bottom_sheet_sort_options)
    binding.viewModel = viewModel
    dialog.setContentView(binding.root)

    return dialog
  }

  fun show(manager: FragmentManager) = super.show(manager, TAG)
}
