package com.blinkist.easylibrary.features.webview

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.blinkist.easylibrary.databinding.WebViewActivityBinding
import com.blinkist.easylibrary.ktx.newIntent
import com.blinkist.easylibrary.ktx.unsyncLazy

class WebViewActivity : AppCompatActivity() {

  companion object {
    private const val extraUrl = "com.blinkist.easylibrary.extraUrl"

    fun newIntent(context: Context, url: String) = context.newIntent<WebViewActivity> {
      putExtra(extraUrl, url)
    }
  }

  private val binding by unsyncLazy { WebViewActivityBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupWebView()
  }

  private fun setupWebView() = with(binding.webView) {
    webViewClient = object : WebViewClient() {
      override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        binding.progressBarContainer.isVisible = true
      }

      override fun onPageFinished(view: WebView, url: String) {
        binding.progressBarContainer.isVisible = false
      }

      override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
        // TODO
      }
    }

    webChromeClient = object : WebChromeClient() {
      override fun onProgressChanged(view: WebView, newProgress: Int) {
        binding.progressBar.progress = newProgress
      }
    }

    loadUrl(intent.getStringExtra(extraUrl))
  }

  override fun onBackPressed() {
    if (binding.webView.canGoBack()) {
      binding.webView.goBack()
    } else {
      super.onBackPressed()
    }
  }
}
