package utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class Prefs {

    companion object{

        var mSharedPref: SharedPreferences? = null

        open fun init(context: Context) {
            if (mSharedPref == null) mSharedPref = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
        }

        fun read(key: String?, defValue: String?): String? {
            return mSharedPref?.getString(key, defValue)
        }

        fun write(key: String?, value: String?) {
            val prefsEditor = mSharedPref?.edit()
            prefsEditor?.putString(key, value)
            prefsEditor?.apply()
        }
    }
}