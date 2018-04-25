package com.blinkist.easylibrary.di

import android.app.Application
import android.content.Context
import com.blinkist.easylibrary.library.LibraryViewModel
import com.blinkist.easylibrary.service.BooksService
import dagger.Component
import dagger.Module
import dagger.Provides
import io.appflate.restmock.RESTMockServer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(viewModel: LibraryViewModel)
}

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun provideApplicationContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideBooksService(): BooksService = Retrofit.Builder()
        .baseUrl(RESTMockServer.getUrl())
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(BooksService::class.java)
}
