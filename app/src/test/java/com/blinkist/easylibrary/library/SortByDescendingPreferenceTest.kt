package com.blinkist.easylibrary.library

import android.content.Context
import android.content.SharedPreferences
import com.blinkist.easylibrary.R
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SortByDescendingPreferenceTest {

    private val key = "key"

    @Mock private lateinit var context: Context
    @Mock private lateinit var sharedPreferences: SharedPreferences
    @Mock private lateinit var editor: SharedPreferences.Editor

    @InjectMocks private lateinit var sortByDescendingPreference: SortByDescendingPreference

    @Before fun setup() {
        given(context.getString(R.string.sort_order_key)).willReturn(key)
        given(sharedPreferences.edit()).willReturn(editor)
        given(editor.putBoolean(anyString(), anyBoolean())).willReturn(editor)
    }

    @Test fun testKeyAndDefaultValue() {
        given(sharedPreferences.getBoolean(key, true)).willReturn(true)
        sortByDescendingPreference.get()
    }

    @Test fun testGettingTrue() {
        given(sharedPreferences.getBoolean(anyString(), anyBoolean())).willReturn(true)

        assertThat(sortByDescendingPreference.get()).isTrue()
    }

    @Test fun testGettingFalse() {
        given(sharedPreferences.getBoolean(anyString(), anyBoolean())).willReturn(false)

        assertThat(sortByDescendingPreference.get()).isFalse()
    }

    @Test fun testSettingTrue() {
        sortByDescendingPreference.set(true)

        then(sharedPreferences).should().edit()
        then(editor).should().putBoolean(key, true)
        then(editor).should().apply()
    }

    @Test fun testSettingFalse() {
        sortByDescendingPreference.set(false)

        then(sharedPreferences).should().edit()
        then(editor).should().putBoolean(key, false)
        then(editor).should().apply()
    }
}
