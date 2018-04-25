package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.service.BooksService
import dagger.Component
import dagger.Module
import dagger.Provides
import org.mockito.BDDMockito.mock
import javax.inject.Singleton

@Singleton
@Component(modules = [TestModule::class])
interface TestComponent : ApplicationComponent

@Module
class TestModule {

    @Provides
    @Singleton
    fun provideBooksService(): BooksService = mock(BooksService::class.java)
}
