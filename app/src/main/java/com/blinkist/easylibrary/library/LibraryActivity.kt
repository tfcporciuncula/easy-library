package com.blinkist.easylibrary.library

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_library.*
import timber.log.Timber

class LibraryActivity : BaseActivity() {

    private val adapter = LibraryAdapter()

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LibraryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        if (savedInstanceState == null) updateBooks()
        swipeRefreshLayout.setOnRefreshListener { updateBooks() }

        recyclerView.adapter = adapter
        viewModel.books().observe(this, Observer { librariables ->
            librariables?.let(adapter::submitList)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_sort) changeBooksSortOrder()
        return super.onOptionsItemSelected(item)
    }

    private fun changeBooksSortOrder() {
        viewModel.booksSortedDifferently()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(::manageDisposable)
            .subscribe(adapter::submitList, Timber::e)
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
        swipeRefreshLayout.isRefreshing = true
    }

    private fun hideProgressBar() {
        swipeRefreshLayout.isRefreshing = false
    }

    private fun showNetworkError() {
        Snackbar.make(recyclerView, R.string.network_error_message, Snackbar.LENGTH_LONG).show()
    }
}
