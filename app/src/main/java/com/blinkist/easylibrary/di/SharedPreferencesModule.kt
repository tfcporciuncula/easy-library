package com.blinkist.easylibrary.di

import android.content.Context
import androidx.preference.PreferenceManager
import com.blinkist.easylibrary.NightThemeManager
import com.blinkist.easylibrary.features.library.LibrarySortOrder
import com.tfcporciuncula.flow.FlowSharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object SharedPreferencesModule {

  @Provides @Singleton
  fun provideFlowSharedPreferences(context: Context) =
    FlowSharedPreferences(PreferenceManager.getDefaultSharedPreferences(context))

  @Qualifier annotation class ThemePreference

  @Provides @ThemePreference
  fun provideThemeOrderPreference(flowSharedPreferences: FlowSharedPreferences) = flowSharedPreferences.getEnum(
    key = "com.blinkist.easylibrary.KEY_THEME",
    defaultValue = NightThemeManager.NightMode.DEFAULT
  )

  @Qualifier annotation class LibrarySortOrderPreference

  @Provides @LibrarySortOrderPreference
  fun provideLibrarySortOrderPreference(flowSharedPreferences: FlowSharedPreferences) = flowSharedPreferences.getEnum(
    key = "com.blinkist.easylibrary.KEY_LIBRARY_SORT_ORDER",
    defaultValue = LibrarySortOrder.DEFAULT
  )
}
