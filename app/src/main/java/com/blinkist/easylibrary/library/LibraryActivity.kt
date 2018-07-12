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

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LibraryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        if (savedInstanceState == null) updateBooks()

        setupSwipeRefreshLayout()
        setupList()
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

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { updateBooks() }
    }

    private fun setupList() {
        recyclerView.adapter = viewModel.adapter
        viewModel.librariables.observe(this, Observer {
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
        swipeRefreshLayout.isRefreshing = true
    }

    private fun hideProgressBar() {
        swipeRefreshLayout.isRefreshing = false
    }

    private fun showNetworkError() {
        Snackbar.make(rootView, R.string.network_error_message, Snackbar.LENGTH_LONG).show()
    }
}
