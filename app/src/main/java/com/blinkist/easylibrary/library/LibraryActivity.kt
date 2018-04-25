package com.blinkist.easylibrary.library

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blinkist.easylibrary.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LibraryActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LibraryViewModel::class.java).apply { init() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("Got ${it.size} books")
            }, {
                Timber.e(it, "Error")
            })
    }
}
