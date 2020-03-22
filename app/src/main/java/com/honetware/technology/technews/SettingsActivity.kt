package com.honetware.technology.technews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val version = findPreference<Preference>(getString(R.string.version_key))
            version?.summary = BuildConfig.VERSION_NAME

        }


        override fun onPreferenceTreeClick(preference: Preference?): Boolean {
            val privacy = getString(R.string.privacy_key)
            val source = getString(R.string.source_key)

            val rate = getString(R.string.rate_key)

            when {
                preference?.key?.equals(privacy)!! -> {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy_url))))
                }
                preference?.key?.equals(source)!! -> {

                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.source_link))))
                }
                preference?.key?.equals(rate)!! -> {
                    try {

                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details/?id=" + context?.packageName)))

                    } catch (e: Exception) {

                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(" https://play.google.com?id=" + context?.packageName)))
                    }
                }
            }
            return super.onPreferenceTreeClick(preference)
        }
    }
}
