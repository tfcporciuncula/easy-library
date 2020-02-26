package com.blinkist.easylibrary

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.blinkist.easylibrary.di.SharedPreferencesModule.ThemePreference
import com.tfcporciuncula.flow.Preference
import javax.inject.Inject

class NightThemeManager @Inject constructor(
  @ThemePreference private val themePreference: Preference<NightMode>
) : Preference<NightThemeManager.NightMode> by themePreference {

  enum class NightMode(val value: Int) {
    LIGHT(MODE_NIGHT_NO),
    DARK(MODE_NIGHT_YES),
    DEFAULT(if (Build.VERSION.SDK_INT >= 29) MODE_NIGHT_FOLLOW_SYSTEM else MODE_NIGHT_AUTO_BATTERY)
  }

  fun initializeNightMode() = AppCompatDelegate.setDefaultNightMode(themePreference.get().value)

  fun setNightMode(nightMode: NightMode) {
    themePreference.set(nightMode)
    initializeNightMode()
  }
}
