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
    context: Context
) : PreferencesProvider(context), LocationProvider {

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {

        val deviceLocationChanged = try {
            isDeviceLocationChanged(lastWeatherLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            false
        }
        return deviceLocationChanged || isCustomLocationChanged(lastWeatherLocation)
    }

    private fun isCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {

        if (!isUsingDeviceLocation()) {
            val customLocationName = getCustomLocationName()
            return lastWeatherLocation.name != customLocationName
        }
        return false
    }

    private fun getCustomLocationName(): String =
        preferences.getString(appContext.getString(R.string.key_custom_location), null)!!

    private suspend fun isDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if (!isUsingDeviceLocation()) return false

        val deviceLocation = getDeviceLocation().await() ?: return false

        val comparisonThreshold = 0.03

        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold ||
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonThreshold
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
        if (isUsingDeviceLocation()) {
            try {
                val deviceLocation = getDeviceLocation().await() ?: return getCustomLocationName()
                return "${deviceLocation.latitude},${deviceLocation.longitude}"
            } catch (e: LocationPermissionNotGrantedException) {
                return getCustomLocationName()
            }
        } else return getCustomLocationName()
    }
}