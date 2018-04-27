package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.library.BookGrouper
import com.blinkist.easylibrary.library.BookGrouperTest
import com.blinkist.easylibrary.library.LibraryAdapter
import com.blinkist.easylibrary.library.LibraryViewModelTest
import com.blinkist.easylibrary.service.LibraryService
import dagger.Component
import dagger.Module
import dagger.Provides
import org.mockito.BDDMockito.mock
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Singleton
@Component(modules = [TestModule::class])
interface TestComponent : ApplicationComponent {

    fun inject(test: LibraryViewModelTest)
    fun inject(test: BookGrouperTest)
}

@Module
class TestModule {

    @Provides
    @Singleton
    fun provideBooksService(): LibraryService = mock(LibraryService::class.java)

    @Provides
    @Singleton
    fun provideBookDao(): BookDao = mock(BookDao::class.java)

    @Provides
    @Singleton
    fun provideBookGrouper(): BookGrouper = mock(BookGrouper::class.java)

    @Provides
    @Singleton
    fun provideLibraryAdapter(): LibraryAdapter = mock(LibraryAdapter::class.java)

    @Provides
    fun provideCalendar(): Calendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
    }

    @Provides
    fun provideDateFormat(): DateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)
}
