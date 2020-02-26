package com.blinkist.easylibrary.features.webview

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.blinkist.easylibrary.databinding.WebViewActivityBinding
import com.blinkist.easylibrary.util.ktx.newIntent
import com.blinkist.easylibrary.util.ktx.unsyncLazy

class WebViewActivity : AppCompatActivity() {

  companion object {
    private const val extraUrl = "com.blinkist.easylibrary.features.webview.extraUrl"

    fun newIntent(context: Context, url: String) = context.newIntent<WebViewActivity> {
      putExtra(extraUrl, url)
    }
  }

  private val binding by unsyncLazy { WebViewActivityBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    hideStatusBar()
    setupWebView()
  }

  private fun hideStatusBar() {
    window.decorView.systemUiVisibility =
      View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
  }

  private fun setupWebView() = with(binding) {
    webView.webViewClient = object : WebViewClient() {
      override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        progressBarContainer.isVisible = true
      }

      override fun onPageFinished(view: WebView, url: String) {
        progressBarContainer.isVisible = false
      }
    }

    webView.webChromeClient = object : WebChromeClient() {
      override fun onProgressChanged(view: WebView, newProgress: Int) {
        progressBar.progress = newProgress
      }
    }

    webView.loadUrl(intent.getStringExtra(extraUrl))
  }

  override fun onBackPressed() = with(binding.webView) {
    if (canGoBack()) goBack() else super.onBackPressed()
  }
}
