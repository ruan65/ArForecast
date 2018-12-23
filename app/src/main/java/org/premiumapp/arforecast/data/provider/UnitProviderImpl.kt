package org.premiumapp.arforecast.data.provider

import android.content.Context
import org.premiumapp.arforecast.R
import org.premiumapp.arforecast.internal.UnitSystem

class UnitProviderImpl(context: Context) : PreferencesProvider(context), UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(
            appContext.getString(R.string.key_unit_system), UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}