package com.example.demowebview

import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

  private lateinit var myWebView: WebView;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    myWebView = findViewById(R.id.webview);
    myWebView.settings.javaScriptEnabled = true
    myWebView.webViewClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url)
        return true
      }

      override fun onPageFinished(view: WebView, url: String) {
        injectCSS();
        super.onPageFinished(view, url)
      }
    }
    myWebView.loadUrl("http://daibaothapmandalataythien.org/lichvutru")
  }

  override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
    if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
      myWebView.goBack()
      return true
    }
    // If it wasn't the Back key or there's no webpage history, bubble up to the default
    // system behavior (probably exit the activity)
    return super.onKeyDown(keyCode, event)
  }

  private fun injectCSS() {
    try {
      val code = """javascript:(function() { 
        var node = document.createElement('style');
        node.type = 'text/css';
        node.innerHTML = '
          .view.lichvutru .menu-fix-bottom {
            display: none;
          }
          #appandroid {
            display: none !important;
          }
        ';
        document.head.appendChild(node);
      })()""".trimIndent()

      myWebView.loadUrl(code)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }
}