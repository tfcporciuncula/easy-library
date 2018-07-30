package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.data.BookDaoTest
import com.blinkist.easylibrary.service.BooksServiceTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RetrofitTestModule::class, DatabaseTestModule::class])
interface TestComponent : ApplicationComponent {

    fun inject(test: BooksServiceTest)
    fun inject(test: BookDaoTest)
}
