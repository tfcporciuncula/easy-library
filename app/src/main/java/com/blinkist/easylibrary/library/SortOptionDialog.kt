package com.blinkist.easylibrary.library

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.base.inflateBinding
import com.blinkist.easylibrary.databinding.BottomSheetSortOptionsBinding

class SortOptionDialog : BottomSheetDialogFragment() {

    companion object {
        val TAG: String = SortOptionDialog::class.java.name

        fun newInstance() = SortOptionDialog()
    }

    private val viewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(LibraryViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val binding = inflateBinding<BottomSheetSortOptionsBinding>(R.layout.bottom_sheet_sort_options)

        binding.viewModel = viewModel
        binding.ascendingTextView.setOnClickListener { onAscendingClick() }
        binding.descendingTextView.setOnClickListener { onDescendingClick() }

        return dialog.apply { setContentView(binding.root) }
    }

    private fun onAscendingClick() = viewModel.rearrangeBooks(sortByDescending = false).also { dismiss() }

    private fun onDescendingClick() = viewModel.rearrangeBooks(sortByDescending = true).also { dismiss() }
}
