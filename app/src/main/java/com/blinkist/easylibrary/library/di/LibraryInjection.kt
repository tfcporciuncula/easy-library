package com.blinkist.easylibrary.library.di

import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.library.BookGrouper
import com.blinkist.easylibrary.library.LibraryActivity
import com.blinkist.easylibrary.library.LibraryAdapter
import com.blinkist.easylibrary.library.LibraryViewModelFactory
import com.blinkist.easylibrary.service.LibraryService
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Subcomponent(modules = [LibraryModule::class])
interface LibraryComponent {

    fun inject(activity: LibraryActivity)
}

@Module
object LibraryModule {

    @JvmStatic @Provides
    fun provideCalendar(): Calendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
    }

    @JvmStatic @Provides
    fun provideDateFormat(): DateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)

    @JvmStatic @Provides
    fun provideBookGrouper(calendar: Calendar, dateFormat: DateFormat) = BookGrouper(calendar, dateFormat)

    @JvmStatic @Provides
    fun provideLibraryAdapter() = LibraryAdapter()

    @JvmStatic @Provides
    fun provideViewModelFactory(
        libraryService: Lazy<LibraryService>,
        bookDao: Lazy<BookDao>,
        bookGrouper: Lazy<BookGrouper>,
        adapter: Lazy<LibraryAdapter>
    ) = LibraryViewModelFactory(libraryService, bookDao, bookGrouper, adapter)
}
