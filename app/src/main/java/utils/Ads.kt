package utils

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.honetware.technology.technews.R

fun loadInterstial(context: Context): InterstitialAd {
    val mInterstitialAd = InterstitialAd(context)
    mInterstitialAd.adUnitId = context.getString(R.string.interstitial)
    mInterstitialAd.loadAd(AdRequest.Builder().build())

    mInterstitialAd.adListener = object: AdListener() {
        override fun onAdClosed() {
            mInterstitialAd.loadAd(AdRequest.Builder().build())
        }
    }
    return mInterstitialAd
}