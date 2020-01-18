package com.blinkist.easylibrary.features.webview

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.blinkist.easylibrary.databinding.WebViewActivityBinding
import com.blinkist.easylibrary.ktx.newIntent

class WebViewActivity : AppCompatActivity() {

  companion object {
    private const val extraUrl = "com.blinkist.easylibrary.extraUrl"

    fun newIntent(context: Context, url: String) = context.newIntent<WebViewActivity> {
      putExtra(extraUrl, url)
    }
  }

  private val url get() = intent.getStringExtra(extraUrl)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = WebViewActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupWebView(binding.webView)
  }

  private fun setupWebView(webView: WebView) {
    webView.loadUrl(url)
  }
}
