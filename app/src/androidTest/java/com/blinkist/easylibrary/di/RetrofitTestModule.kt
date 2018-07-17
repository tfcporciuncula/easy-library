package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.service.BooksService
import io.appflate.restmock.RESTMockServer

class RetrofitTestModule : RetrofitModule() {

    override fun provideBaseUrl(): String = RESTMockServer.getUrl()

    fun buildLibraryService(): BooksService {
        return provideBooksService(
            provideBaseUrl(),
            provideHttpClient(),
            provideConverterFactory(),
            provideCallAdapterFactory()
        )
    }
}
