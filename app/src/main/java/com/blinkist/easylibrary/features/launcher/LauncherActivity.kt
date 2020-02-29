package com.blinkist.easylibrary.features.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blinkist.easylibrary.features.library.LibraryActivity

class LauncherActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    startActivity(Intent(this, LibraryActivity::class.java))
    finish()
  }
}
