package com.honetware.technology.technews

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_web_view)

        val url:String = intent.getStringExtra("url")
        val myWebView: WebView = findViewById(R.id.webview)
        val mProgressBar: ProgressBar = findViewById(R.id.progressBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            mProgressBar.progressTintList = ColorStateList.valueOf(Color.BLUE)
        };

        myWebView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                // Update the progress bar with page loading progress
                mProgressBar.progress = newProgress
                if (newProgress == 100) {
                    // Hide the progressbar
                    mProgressBar.visibility = View.GONE
                }
            }
        }

        myWebView.settings.javaScriptEnabled = true

        myWebView.loadUrl(url)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(intent.getStringExtra("notification") != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
