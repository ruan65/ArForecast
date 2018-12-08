package org.premiumapp.arforecast.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import org.premiumapp.arforecast.R
import org.premiumapp.arforecast.internal.UnitSystem

class UnitProviderImpl(context: Context) : UnitProvider {

    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(appContext.getString(R.string.key_unit_system), UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}