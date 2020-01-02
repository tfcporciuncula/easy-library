package com.blinkist.easylibrary.features.library

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.blinkist.easylibrary.databinding.LibrarySortOptionDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private val TAG: String = LibrarySortOptionBottomSheetDialog::class.java.name

class LibrarySortOptionBottomSheetDialog : BottomSheetDialogFragment() {

  companion object {
    fun show(manager: FragmentManager) {
      val fragment = manager.findFragmentByTag(TAG) as LibrarySortOptionBottomSheetDialog?
        ?: LibrarySortOptionBottomSheetDialog()
      fragment.show(manager, TAG)
    }
  }

  private val viewModel by activityViewModels<LibraryViewModel>()

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState)
    val binding = LibrarySortOptionDialogBinding.inflate(LayoutInflater.from(requireContext()), null, false)
    setupUi(binding)
    dialog.setContentView(binding.root)
    return dialog
  }

  private fun setupUi(binding: LibrarySortOptionDialogBinding) {
    binding.ascendingTextView.setOnClickListener { onSortOrderClicked(LibrarySortOrder.ASCENDING) }
    binding.descendingTextView.setOnClickListener { onSortOrderClicked(LibrarySortOrder.DESCENDING) }
    viewModel.select { currentSortOrder }.observe(this) { setSelectedSortOrder(binding, it) }
  }

  private fun onSortOrderClicked(sortOrder: LibrarySortOrder) = when (sortOrder) {
    LibrarySortOrder.ASCENDING -> viewModel.onArrangeByAscendingClicked()
    LibrarySortOrder.DESCENDING -> viewModel.onArrangeByDescendingClicked()
  }.also { dismiss() }

  private fun setSelectedSortOrder(binding: LibrarySortOptionDialogBinding, sortOrder: LibrarySortOrder) {
    binding.ascendingCheckImageView.isVisible = sortOrder == LibrarySortOrder.ASCENDING
    binding.descendingCheckImageView.isVisible = sortOrder == LibrarySortOrder.DESCENDING
  }
}
