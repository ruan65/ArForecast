package org.premiumapp.arforecast.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred
import org.premiumapp.arforecast.R
import org.premiumapp.arforecast.data.db.entity.WeatherLocation
import org.premiumapp.arforecast.internal.LocationPermissionNotGrantedException
import org.premiumapp.arforecast.internal.asDeferred

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

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation(): Deferred<Location?> =
        if (hasLocationPermission()) fusedLocationProviderClient.lastLocation.asDeferred()
        else throw LocationPermissionNotGrantedException()


    private fun hasLocationPermission(): Boolean =
        ContextCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(appContext.getString(R.string.key_use_device_location), false)
    }

    override suspend fun getPreferredLocation(): String {
        return "Moscow"
    }
}