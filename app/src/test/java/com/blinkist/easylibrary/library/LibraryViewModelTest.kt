package com.blinkist.easylibrary.library

import com.blinkist.easylibrary.base.BaseTest
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.model.ModelFactory.newBook
import com.blinkist.easylibrary.service.LibraryService
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import javax.inject.Inject

class LibraryViewModelTest : BaseTest() {

    @Inject
    lateinit var libraryService: LibraryService

    @Inject
    lateinit var bookDao: BookDao

    private val viewModel get() = LibraryViewModel(application)

    @Before
    override fun setup() {
        super.setup()
        component.inject(this)
    }

    @Test
    fun testBooks() {

    }

    @Test
    fun testUpdateBooks() {
        val books = listOf(
            newBook(title = "book1"),
            newBook(title = "book2")
        )

        given(libraryService.books()).willReturn(Single.just(books))

        viewModel.updateBooks().test().assertNoErrors()
        verify(libraryService).books()
        verify(bookDao).clear()
        verify(bookDao).insert(books)
    }
}
