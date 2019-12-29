package com.blinkist.easylibrary.features.library

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.databinding.ActivityLibraryBinding
import com.blinkist.easylibrary.di.injector
import com.blinkist.easylibrary.di.lazyViewModel
import com.blinkist.easylibrary.features.library.LibraryViewState.ErrorEvent
import com.blinkist.easylibrary.ktx.showSnackbar
import com.blinkist.easylibrary.ktx.unsyncLazy

class LibraryActivity : AppCompatActivity() {

  private val binding by unsyncLazy {
    DataBindingUtil.setContentView<ActivityLibraryBinding>(this, R.layout.activity_library)
  }

  private val sortOptionDialog by unsyncLazy { LibrarySortOptionBottomSheetDialog.newInstance() }

  private val viewModel by lazyViewModel { injector.libraryViewModel }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupUi()
  }

  private fun setupUi() {
    binding.lifecycleOwner = this
    binding.viewModel = viewModel

    observeViewState()
  }

  private fun observeViewState() {
    val adapter = LibraryAdapter(onItemClicked = viewModel::onItemClicked)
    binding.recyclerView.adapter = adapter

    viewModel.state().observe(this) { state ->
      adapter.submitList(state.libraryItems)

      state.errorEvent?.let { event ->
        event.doIfNotHandled { showErrorMessage(event) }
      }
      state.sortDialogClickedEvent?.let { event ->
        event.doIfNotHandled { sortOptionDialog.dismiss() }
      }
    }
  }

  private fun showErrorMessage(errorEvent: ErrorEvent) {
    when (errorEvent) {
      is ErrorEvent.Network -> binding.root.showSnackbar(R.string.network_error_message)
      is ErrorEvent.Unexpected -> binding.root.showSnackbar(R.string.unexpected_error_message)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.menu_sort -> {
      sortOptionDialog.show(supportFragmentManager)
      true
    }
    else -> super.onOptionsItemSelected(item)
  }
}
