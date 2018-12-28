package com.blinkist.easylibrary.library

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.databinding.ActivityLibraryBinding
import com.blinkist.easylibrary.di.getViewModel
import com.blinkist.easylibrary.di.injector
import com.blinkist.easylibrary.livedata.observe
import com.google.android.material.snackbar.Snackbar

class LibraryActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupUi(
      binding = DataBindingUtil.setContentView(this, R.layout.activity_library),
      isInitialLaunch = savedInstanceState == null
    )
  }

  private fun setupUi(binding: ActivityLibraryBinding, isInitialLaunch: Boolean) {
    val viewModel = getViewModel { injector.libraryViewModel }
    binding.viewModel = viewModel
    binding.setLifecycleOwner(this)

    if (isInitialLaunch) viewModel.updateBooks()

    viewModel.state().observe(this) { state ->
      state.error?.let {
        it.doIfNotHandled { showNetworkError(binding) }
      }
      viewModel.adapter.submitList(state.books)
    }
  }

  private fun showNetworkError(binding: ActivityLibraryBinding) {
    Snackbar.make(binding.root, R.string.network_error_message, Snackbar.LENGTH_LONG).show()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.menu_sort -> {
      SortOptionDialog.newInstance().show(supportFragmentManager, SortOptionDialog.TAG)
      true
    }
    else -> super.onOptionsItemSelected(item)
  }
}
