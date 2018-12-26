package com.blinkist.easylibrary.di

import android.content.Context
import com.blinkist.easylibrary.R
import dagger.Module
import dagger.Provides

@Module
object SharedPreferencesModule {

    @JvmStatic @Provides
    fun provideLibrarySharedPreferences(context: Context) = context.getSharedPreferences(
        context.getString(R.string.library_preferences_name),
        Context.MODE_PRIVATE
    )
}
