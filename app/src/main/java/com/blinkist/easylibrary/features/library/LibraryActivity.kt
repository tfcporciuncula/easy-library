package com.blinkist.easylibrary.features.library

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.databinding.ActivityLibraryBinding
import com.blinkist.easylibrary.di.injector
import com.blinkist.easylibrary.di.viewModels
import com.blinkist.easylibrary.ktx.unsyncLazy
import com.google.android.material.snackbar.Snackbar

class LibraryActivity : AppCompatActivity() {

  private val binding by unsyncLazy {
    DataBindingUtil.setContentView<ActivityLibraryBinding>(this, R.layout.activity_library)
  }

  private val viewModel by viewModels { injector.libraryViewModel }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupUi(isInitialLaunch = savedInstanceState == null)
  }

  private fun setupUi(isInitialLaunch: Boolean) {
    binding.lifecycleOwner = this
    binding.viewModel = viewModel
    viewModel.state().observe(this, Observer { handleState(it) })

    if (isInitialLaunch) viewModel.updateBooks()
  }

  private fun handleState(state: LibraryViewState) {
    viewModel.adapter.submitList(state.books)

    state.error?.let { error ->
      error.doIfNotHandled { show(error) }
    }
  }

  private fun show(error: LibraryError) {
    when (error) {
      is LibraryError.Network -> showSnackbar(R.string.network_error_message)
      is LibraryError.Unexpected -> showSnackbar(R.string.unexpected_error_message)
    }
  }

  private fun showSnackbar(@StringRes resId: Int) =
    Snackbar.make(binding.root, resId, Snackbar.LENGTH_LONG).show()

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
