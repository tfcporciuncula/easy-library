package com.blinkist.easylibrary.features.library

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.databinding.LibraryActivityBinding
import com.blinkist.easylibrary.databinding.LibraryActivityContainerBinding
import com.blinkist.easylibrary.di.injector
import com.blinkist.easylibrary.features.library.LibraryViewState.NavigationEvent
import com.blinkist.easylibrary.features.webview.WebViewActivity
import com.blinkist.easylibrary.util.ktx.exhaustive
import com.blinkist.easylibrary.util.ktx.lazyViewModel
import com.blinkist.easylibrary.util.ktx.observeEvent
import com.blinkist.easylibrary.util.ktx.observeOnTrue
import com.blinkist.easylibrary.util.ktx.showSnackbar

class LibraryActivity : AppCompatActivity() {

  private val viewModel by lazyViewModel { injector.libraryViewModel }

  override fun onCreate(savedInstanceState: Bundle?) {
    resetThemeFromSplashScreen()
    super.onCreate(savedInstanceState)
    val binding = LibraryActivityContainerBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupUi(binding.libraryActivity)
  }

  private fun resetThemeFromSplashScreen() = setTheme(R.style.AppTheme)

  override fun recreate() = restartActivityWithFadeWhenThemeChanges()

  private fun restartActivityWithFadeWhenThemeChanges() {
    finish()
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    startActivity(intent)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
  }

  private fun setupUi(binding: LibraryActivityBinding) {
    setupToolbar(binding)
    setupThemePopup()
    setupSwipeRefreshLayout(binding)
    setupRecyclerView(binding)
    observeSnackbarEvents(binding)
    observeNavigationEvents()
  }

  private fun setupToolbar(binding: LibraryActivityBinding) = with(binding.toolbar) {
    setOnMenuItemClickListener(::onMenuItemClicked)
  }

  private fun onMenuItemClicked(item: MenuItem) = when (item.itemId) {
    R.id.menu_sort -> {
      viewModel.onSortMenuItemClicked(); true
    }
    R.id.menu_theme -> {
      viewModel.onThemeMenuItemClicked(); true
    }
    else -> false
  }

  private fun setupThemePopup() {
    viewModel.select { isThemePopupOpen }.observeOnTrue(this) {
      ThemePopup.show(
        context = this,
        anchor = findViewById(R.id.menu_theme),
        onItemClicked = viewModel::onThemePopupItemClicked,
        onDismissed = viewModel::onThemePopupDismissed
      )
    }
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
      when (it) {
        is NavigationEvent.ToSortOrderDialog -> LibrarySortOrderBottomSheetDialog.show(supportFragmentManager)
        is NavigationEvent.ToWebView -> startActivity(WebViewActivity.newIntent(this, it.url))
      }.exhaustive
    }
  }
}
