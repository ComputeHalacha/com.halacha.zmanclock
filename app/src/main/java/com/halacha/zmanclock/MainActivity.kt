package com.halacha.zmanclock

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import androidx.webkit.WebViewClientCompat

class MainActivity  : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get a reference to the WebView
        val webView: WebView = findViewById(R.id.webView)
        val assetLoader = WebViewAssetLoader.Builder()
            .setDomain("zmanclock.net")
            .addPathHandler("/assets/", AssetsPathHandler(this))
            .build()
        webView.webViewClient = LocalContentWebViewClient(assetLoader)

        webView.settings.javaScriptEnabled = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowContentAccess = true

        webView.loadUrl("https://zmanclock.net/assets/index.html")
    }
}

private class LocalContentWebViewClient(private val assetLoader: WebViewAssetLoader) : WebViewClientCompat() {
    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
        return assetLoader.shouldInterceptRequest(request.url)
    }

    // To support API < 21.
    @Deprecated("")
    override fun shouldInterceptRequest(
        view: WebView,
        url: String
    ): WebResourceResponse? {
        return assetLoader.shouldInterceptRequest(Uri.parse(url))
    }
}
