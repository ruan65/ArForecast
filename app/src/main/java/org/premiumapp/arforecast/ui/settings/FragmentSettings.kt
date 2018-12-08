package org.premiumapp.arforecast.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import org.premiumapp.arforecast.R

class FragmentSettings : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val supportActionBar = (activity as AppCompatActivity)?.supportActionBar
        supportActionBar?.title = "Settings"
        supportActionBar?.subtitle = null

    }


}