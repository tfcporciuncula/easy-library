package com.blinkist.easylibrary.features.library

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.databinding.LibraryActivityBinding
import com.blinkist.easylibrary.di.injector
import com.blinkist.easylibrary.di.lazyViewModel
import com.blinkist.easylibrary.ktx.observeEvent
import com.blinkist.easylibrary.ktx.showSnackbar

class LibraryActivity : AppCompatActivity() {

  private val viewModel by lazyViewModel { injector.libraryViewModel }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = LibraryActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupUi(binding)
  }

  private fun setupUi(binding: LibraryActivityBinding) {
    setupSwipeRefreshLayout(binding)
    setupRecyclerView(binding)
    observeSnackbarEvents(binding)
  }

  private fun setupSwipeRefreshLayout(binding: LibraryActivityBinding) {
    binding.swipeRefreshLayout.setOnRefreshListener { viewModel.updateBooks() }
    viewModel.select { isLoading }.observe(this, binding.swipeRefreshLayout::setRefreshing)
  }

  private fun setupRecyclerView(binding: LibraryActivityBinding) {
    val adapter = LibraryAdapter(onBookClicked = viewModel::onItemClicked)
    binding.recyclerView.adapter = adapter
    viewModel.select { libraryItems }.observe(this, adapter::submitList)
  }

  private fun observeSnackbarEvents(binding: LibraryActivityBinding) {
    viewModel.select { snackbarEvent }.observeEvent(this, binding.root::showSnackbar)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.menu_sort -> {
      LibrarySortOptionBottomSheetDialog.show(supportFragmentManager)
      true
    }
    else -> super.onOptionsItemSelected(item)
  }
}
