package com.blinkist.easylibrary.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.data.EasyLibraryDatabase
import com.blinkist.easylibrary.library.BookGrouper
import com.blinkist.easylibrary.library.LibraryActivity
import com.blinkist.easylibrary.library.LibraryAdapter
import com.blinkist.easylibrary.library.LibraryViewModelFactory
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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(activity: LibraryActivity)
}

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application.applicationContext

    @Provides
    fun provideBaseUrl(): String = RESTMockServer.getUrl()

    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
    }

    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideBooksService(
        baseUrl: String,
        httpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory
    ): LibraryService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .build()
        .create(LibraryService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(context: Context): EasyLibraryDatabase {
        return Room.databaseBuilder(context, EasyLibraryDatabase::class.java, "easy-library").build()
    }

    @Provides
    fun provideBookDao(database: EasyLibraryDatabase) = database.bookDao()

    @Provides
    fun provideCalendar(): Calendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
    }

    @Provides
    fun provideDateFormat(): DateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)

    @Provides
    fun provideBookGrouper(calendar: Calendar, dateFormat: DateFormat) = BookGrouper(calendar, dateFormat)

    @Provides
    fun provideLibraryAdapter() = LibraryAdapter()

    @Provides
    fun provideViewModelFactory(
        libraryService: LibraryService,
        bookDao: BookDao,
        bookGrouper: BookGrouper,
        adapter: LibraryAdapter
    ): LibraryViewModelFactory {
        return LibraryViewModelFactory(libraryService, bookDao, bookGrouper, adapter)
    }
}
