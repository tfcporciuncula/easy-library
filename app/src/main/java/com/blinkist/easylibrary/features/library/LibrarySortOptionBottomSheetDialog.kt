package com.blinkist.easylibrary.features.library

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.databinding.DialogLibrarySortOptionBinding
import com.blinkist.easylibrary.databinding.inflateBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private val TAG: String = LibrarySortOptionBottomSheetDialog::class.java.name

class LibrarySortOptionBottomSheetDialog : BottomSheetDialogFragment() {

  companion object {
    fun getInstance(manager: FragmentManager) =
      manager.findFragmentByTag(TAG) as LibrarySortOptionBottomSheetDialog? ?: LibrarySortOptionBottomSheetDialog()
  }

  private val viewModel by activityViewModels<LibraryViewModel>()

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState)

    val binding = inflateBinding<DialogLibrarySortOptionBinding>(R.layout.dialog_library_sort_option)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel
    dialog.setContentView(binding.root)

    return dialog
  }

  fun show(manager: FragmentManager) = super.show(manager, TAG)
}
