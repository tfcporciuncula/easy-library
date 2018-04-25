package com.blinkist.easylibrary.base

import com.blinkist.easylibrary.EasyLibraryApplication
import com.blinkist.easylibrary.di.DaggerTestComponent
import com.blinkist.easylibrary.di.TestModule
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class BaseTest {

    private val component by lazy {
        DaggerTestComponent.builder()
            .testModule(TestModule())
            .build()
    }

    @Mock
    protected lateinit var application: EasyLibraryApplication

    @Before
    fun setup() {
        given(application.component).willReturn(component)
    }
}
