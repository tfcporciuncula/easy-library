package com.blinkist.easylibrary.features.library

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.databinding.LibraryActivityBinding
import com.blinkist.easylibrary.di.injector
import com.blinkist.easylibrary.features.webview.WebViewActivity
import com.blinkist.easylibrary.util.ktx.lazyViewModel
import com.blinkist.easylibrary.util.ktx.observeEvent
import com.blinkist.easylibrary.util.ktx.showSnackbar

class LibraryActivity : AppCompatActivity() {

  private val viewModel by lazyViewModel { injector.libraryViewModel }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = LibraryActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupUi(binding)
  }

  private fun setupUi(binding: LibraryActivityBinding) {
    setupToolbar(binding)
    setupSwipeRefreshLayout(binding)
    setupRecyclerView(binding)
    observeSnackbarEvents(binding)
    observeNavigationEvents()
  }

  private fun setupToolbar(binding: LibraryActivityBinding) = with(binding.toolbar) {
    setTitle(R.string.app_name)
    inflateMenu(R.menu.menu)
    setOnMenuItemClickListener(::onMenuItemClicked)
  }

  private fun onMenuItemClicked(item: MenuItem) = when (item.itemId) {
    R.id.menu_sort -> {
      LibrarySortOptionBottomSheetDialog.show(supportFragmentManager)
      true
    }
    else -> false
  }

  private fun setupSwipeRefreshLayout(binding: LibraryActivityBinding) {
    binding.swipeRefreshLayout.setOnRefreshListener(viewModel::updateBooks)
    viewModel.select { isLoading }.observe(this, binding.swipeRefreshLayout::setRefreshing)
  }

  private fun setupRecyclerView(binding: LibraryActivityBinding) {
    val adapter = LibraryAdapter(onBookClicked = viewModel::onBookClicked)
    binding.recyclerView.adapter = adapter
    viewModel.select { libraryItems }.observe(this, adapter::submitList)
  }

  private fun observeSnackbarEvents(binding: LibraryActivityBinding) {
    viewModel.select { snackbarEvent }.observeEvent(this) {
      binding.root.showSnackbar(it.messageResId)
    }
  }

  private fun observeNavigationEvents() {
    viewModel.select { navigationEvent }.observeEvent(this) {
      startActivity(WebViewActivity.newIntent(this, it.url))
    }
  }
}
