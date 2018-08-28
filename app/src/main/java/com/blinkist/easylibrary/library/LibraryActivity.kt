package com.blinkist.easylibrary.library

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.base.BaseActivity
import com.blinkist.easylibrary.databinding.ActivityLibraryBinding
import com.blinkist.easylibrary.di.injector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LibraryActivity : BaseActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, injector.libraryViewModelFactory()).get(LibraryViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityLibraryBinding>(this, R.layout.activity_library)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi()

        if (savedInstanceState == null) updateBooks()
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

    private fun setupUi() {
        binding.swipeRefreshLayout.setOnRefreshListener { updateBooks() }
        binding.viewModel = viewModel
        viewModel.books().observe(this, Observer {
            it?.let(viewModel.adapter::submitList)
        })
    }

    private fun updateBooks() {
        viewModel.updateBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                manageDisposable(it)
                showProgressBar()
            }
            .subscribe({
                hideProgressBar()
            }, {
                Timber.e(it)
                hideProgressBar()
                showNetworkError()
            })
    }

    private fun showProgressBar() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun hideProgressBar() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun showNetworkError() {
        Snackbar.make(binding.root, R.string.network_error_message, Snackbar.LENGTH_LONG).show()
    }
}
