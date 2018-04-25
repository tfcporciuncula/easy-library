package com.blinkist.easylibrary.library

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.service.BooksService
import io.appflate.restmock.RESTMockServer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
