package com.honetware.technology.technews

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.InterstitialAd
import im.delight.android.webview.AdvancedWebView
import utils.loadInterstial

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class WebViewActivity : AppCompatActivity(), AdvancedWebView.Listener {
    lateinit var mProgressBar: ProgressBar
    lateinit var myWebView: AdvancedWebView
    lateinit var interstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_web_view)

        val url:String = intent.getStringExtra("url").toString()
        interstitialAd = loadInterstial(this)

        myWebView = findViewById(R.id.webview)
        mProgressBar = findViewById(R.id.progressBar)

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

        if (url.isNotBlank() && url != ""){
            myWebView.loadUrl(url)
        }else{
            finish()
            Toast.makeText(this,"Unable to open link",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(intent.getStringExtra("notification") != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else{
            if (interstitialAd.isLoaded){
                val adListener = object : AdListener(){
                    override fun onAdClosed() {
                        val intent = Intent(this@WebViewActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                interstitialAd.adListener = adListener
                interstitialAd.show()
            }else{
                val intent = Intent(this@WebViewActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onPageFinished(url: String?) {
        mProgressBar.visibility = View.GONE
    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {
       Toast.makeText(this,description + "",Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onDownloadRequested(url: String?, suggestedFilename: String?, mimeType: String?, contentLength: Long, contentDisposition: String?, userAgent: String?) {
        Log.d("msg_tag","Download requested")
    }

    override fun onExternalPageRequest(url: String?) {
        Log.d("msg_tag","external page request")
    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {
        mProgressBar.visibility = View.VISIBLE
    }

}
