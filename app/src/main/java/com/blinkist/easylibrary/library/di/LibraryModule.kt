package com.blinkist.easylibrary.library.di

import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.di.ApplicationModule
import com.blinkist.easylibrary.di.DatabaseModule
import com.blinkist.easylibrary.di.RetrofitModule
import com.blinkist.easylibrary.library.BookGrouper
import com.blinkist.easylibrary.library.LibraryAdapter
import com.blinkist.easylibrary.library.LibraryViewModelFactory
import com.blinkist.easylibrary.service.LibraryService
import dagger.Module
import dagger.Provides
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Module(includes = [ApplicationModule::class, RetrofitModule::class, DatabaseModule::class])
object LibraryModule {

    @JvmStatic
    @Provides
    fun provideCalendar(): Calendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
    }

    @JvmStatic
    @Provides
    fun provideDateFormat(): DateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)

    @JvmStatic
    @Provides
    fun provideBookGrouper(calendar: Calendar, dateFormat: DateFormat) = BookGrouper(calendar, dateFormat)

    @JvmStatic
    @Provides
    fun provideLibraryAdapter() = LibraryAdapter()

    @JvmStatic
    @Provides
    fun provideViewModelFactory(
        libraryService: LibraryService,
        bookDao: BookDao,
        bookGrouper: BookGrouper,
        adapter: LibraryAdapter
    ) = LibraryViewModelFactory(libraryService, bookDao, bookGrouper, adapter)
}
