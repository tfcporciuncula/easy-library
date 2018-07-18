package com.blinkist.easylibrary.library.di

import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.library.BookGrouper
import com.blinkist.easylibrary.library.LibraryActivity
import com.blinkist.easylibrary.library.LibraryAdapter
import com.blinkist.easylibrary.library.LibraryViewModelFactory
import com.blinkist.easylibrary.model.BookMapper
import com.blinkist.easylibrary.service.BooksService
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Qualifier

@Subcomponent(modules = [LibraryModule::class])
interface LibraryComponent {

    fun inject(activity: LibraryActivity)
}

@Module
object LibraryModule {

    @Qualifier
    private annotation class Internal

    @JvmStatic @Provides @Internal
    fun provideCalendar(): Calendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
    }

    @JvmStatic @Provides @Internal
    fun provideDateFormat(): DateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)

    @JvmStatic @Provides @Internal
    fun provideBookGrouper(@Internal calendar: Calendar, @Internal dateFormat: DateFormat) =
        BookGrouper(calendar, dateFormat)

    @JvmStatic @Provides @Internal
    fun provideLibraryAdapter() = LibraryAdapter()

    @JvmStatic @Provides
    fun provideViewModelFactory(
        booksService: Lazy<BooksService>,
        bookMapper: Lazy<BookMapper>,
        bookDao: Lazy<BookDao>,
        @Internal bookGrouper: Lazy<BookGrouper>,
        @Internal adapter: Lazy<LibraryAdapter>
    ) = LibraryViewModelFactory(booksService, bookMapper, bookDao, bookGrouper, adapter)
}
