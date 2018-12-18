package org.premiumapp.arforecast.data.provider

import org.premiumapp.arforecast.data.db.entity.WeatherLocation

class LocationProviderImpl : LocationProvider {

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocation(): String {
        return "Moscow"
    }
}