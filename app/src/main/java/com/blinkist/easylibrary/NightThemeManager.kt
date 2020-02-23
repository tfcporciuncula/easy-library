package com.blinkist.easylibrary

import androidx.appcompat.app.AppCompatDelegate
import com.blinkist.easylibrary.di.SharedPreferencesModule.ThemePreference
import com.tfcporciuncula.flow.Preference
import javax.inject.Inject

class NightThemeManager @Inject constructor(
  @ThemePreference private val themePreference: Preference<NightMode>
) : Preference<NightThemeManager.NightMode> by themePreference {

  enum class NightMode(val value: Int) {
    LIGHT(AppCompatDelegate.MODE_NIGHT_NO),
    DARK(AppCompatDelegate.MODE_NIGHT_YES),
    AUTO_BATTERY(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY),
    SYSTEM_DEFAULT(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
  }

  fun initializeNightMode() = AppCompatDelegate.setDefaultNightMode(themePreference.get().value)
}
