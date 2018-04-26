package com.blinkist.easylibrary.library

import com.blinkist.easylibrary.base.BaseTest
import io.reactivex.subjects.PublishSubject
import org.junit.Test

class LibraryTest : BaseTest() {

    @Test
    fun test() {
        val viewModel = LibraryViewModel(application)
        val viewModel2 = LibraryViewModel(application)
    }
}
