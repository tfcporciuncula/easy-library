package com.blinkist.easylibrary.di

import android.content.Context
import androidx.preference.PreferenceManager
import com.tfcporciuncula.flow.FlowSharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object SharedPreferencesModule {

  @Provides @Singleton
  fun provideFlowSharedPreferences(
    context: Context
  ) = FlowSharedPreferences(PreferenceManager.getDefaultSharedPreferences(context))

  @Qualifier annotation class SortByDescending

  @Provides @SortByDescending
  fun provideSortByDescendingPreference(flowSharedPreferences: FlowSharedPreferences) =
    flowSharedPreferences.getBoolean("com.blinkist.easylibrary.KEY_SORT_ORDER", defaultValue = false)
}
