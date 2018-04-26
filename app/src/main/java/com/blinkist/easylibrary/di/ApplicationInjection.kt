package com.blinkist.easylibrary.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.blinkist.easylibrary.data.EasyLibraryDatabase
import com.blinkist.easylibrary.library.LibraryViewModel
import com.blinkist.easylibrary.service.LibraryService
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Component
import dagger.Module
import dagger.Provides
import io.appflate.restmock.RESTMockServer
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
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
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
    }

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideBooksService(
        httpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory
    ): LibraryService {
        return Retrofit.Builder()
            .baseUrl(RESTMockServer.getUrl())
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
            .create(LibraryService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): EasyLibraryDatabase {
        return Room.databaseBuilder(context, EasyLibraryDatabase::class.java, "easy-library").build()
    }

    @Provides
    @Singleton
    fun provideBookDao(database: EasyLibraryDatabase) = database.bookDao()
}
