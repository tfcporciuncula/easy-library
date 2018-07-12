package com.blinkist.easylibrary.library

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View
import com.blinkist.easylibrary.R
import kotlinx.android.synthetic.main.bottom_sheet_sort_options.view.*

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
        val view = View.inflate(context, R.layout.bottom_sheet_sort_options, null)
        dialog.setContentView(view)

        setupUi(view)

        return dialog
    }

    private fun setupUi(view: View) {
        if (viewModel.sortByDescending) {
            view.descendingCheckIcon.visibility = View.VISIBLE
        } else {
            view.ascendingCheckIcon.visibility = View.VISIBLE
        }

        view.ascendingTextView.setOnClickListener { onAscendingClick() }
        view.descendingTextView.setOnClickListener { onDescendingClick() }
    }

    private fun onAscendingClick() {
        if (viewModel.sortByDescending) {
            viewModel.rearrangeBooks(sortByDescending = false)
        }
        dismiss()
    }

    private fun onDescendingClick() {
        if (!viewModel.sortByDescending) {
            viewModel.rearrangeBooks(sortByDescending = true)
        }
        dismiss()
    }
}
