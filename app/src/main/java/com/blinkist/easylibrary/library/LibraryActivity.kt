package com.blinkist.easylibrary.library

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.livedata.observe
import com.blinkist.easylibrary.databinding.ActivityLibraryBinding
import com.blinkist.easylibrary.di.injector

class LibraryActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, injector.libraryViewModelFactory()).get(LibraryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLibraryBinding>(this, R.layout.activity_library)

        setupUi(binding, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_sort) {
            SortOptionDialog.newInstance().show(supportFragmentManager, SortOptionDialog.TAG)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUi(binding: ActivityLibraryBinding, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) viewModel.updateBooks()

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

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
}
