package com.blinkist.easylibrary.base

import com.blinkist.easylibrary.EasyLibraryApplication
import com.blinkist.easylibrary.di.DaggerTestComponent
import com.blinkist.easylibrary.di.TestComponent
import com.blinkist.easylibrary.di.TestModule
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class InjectionAwareTest {

    protected lateinit var component: TestComponent

    @Mock
    protected lateinit var application: EasyLibraryApplication

    @Before
    open fun setup() {
        component = DaggerTestComponent.builder()
            .testModule(TestModule())
            .build()

        given(application.component).willReturn(component)
    }
}