package org.premiumapp.arforecast.data.provider

import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred
import org.premiumapp.arforecast.R
import org.premiumapp.arforecast.data.db.entity.WeatherLocation

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context) : PreferencesProvider(context), LocationProvider {

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {

        val deviceLocationChanged = isDeviceLocationChanged()
        return deviceLocationChanged || isCustomLocationChanged()
    }

    private fun isCustomLocationChanged(): Boolean {


    }

    private fun isDeviceLocationChanged(): Boolean {
        if (!isUsingDeviceLocation()) return false

        val deviceLocation = getDeviceLocation().await() ?: return false

        val comparisonThreshold = 0.03
    }

    private fun getDeviceLocation(): Deferred<Location?> {
        return fusedLocationProviderClient.lastLocation
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(appContext.getString(R.string.key_use_device_location), false)
    }

    override suspend fun getPreferredLocation(): String {
        return "Moscow"
    }
}