package com.blinkist.easylibrary.library

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.blinkist.easylibrary.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_library.*

class LibraryActivity : AppCompatActivity() {

    private var updateBooksDisposable = Disposables.empty()

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LibraryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        if (savedInstanceState == null) updateBooks()
        swipeRefreshLayout.setOnRefreshListener { updateBooks() }

        val adapter = LibraryAdapter()
        recyclerView.adapter = adapter
        viewModel.books()
            .observe(this, Observer { books ->
                books?.let { adapter.submitList(it) }
            })
    }

    private fun updateBooks() {
        updateBooksDisposable = viewModel.updateBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showProgressBar() }
            .subscribe({
                hideProgressBar()
            }, {
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

    override fun onDestroy() {
        updateBooksDisposable.dispose()
        super.onDestroy()
    }
}
