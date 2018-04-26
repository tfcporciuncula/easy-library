package com.blinkist.easylibrary.library

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.blinkist.easylibrary.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LibraryActivity : AppCompatActivity() {

    private var updateBooksDisposable = Disposables.empty()

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LibraryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            updateBooks()
        }

        viewModel.books()
            .observe(this, Observer {
                Timber.d("Books changed")
                Toast.makeText(this, "Got ${it?.size} books!", Toast.LENGTH_LONG).show()
            })
    }

    private fun updateBooks() {
        updateBooksDisposable = viewModel.updateBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                Timber.d("Show loading")
                showProgressBar()
            }
            .subscribe({
                Timber.d("Books updated successfully")
                hideProgressBar()
            }, {
                Timber.e(it, "Error while updating books")
                showNetworkError()
            })
    }

    private fun showProgressBar() {

    }

    private fun hideProgressBar() {

    }

    private fun showNetworkError() {

    }

    override fun onDestroy() {
        updateBooksDisposable.dispose()
        super.onDestroy()
    }
}
