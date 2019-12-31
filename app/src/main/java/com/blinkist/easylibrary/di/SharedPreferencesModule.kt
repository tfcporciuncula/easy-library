package com.blinkist.easylibrary.di

import android.content.Context
import androidx.preference.PreferenceManager
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

  @Qualifier annotation class LibrarySortOrderPreference

  @Provides @LibrarySortOrderPreference
  fun provideLibrarySortOrderPreference(flowSharedPreferences: FlowSharedPreferences) =
    flowSharedPreferences.getEnum("com.blinkist.easylibrary.KEY_SORT_ORDER", defaultValue = LibrarySortOrder.DEFAULT)
}
