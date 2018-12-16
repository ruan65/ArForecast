package org.premiumapp.arforecast.data.network.response

import org.premiumapp.arforecast.data.db.entity.CurrentWeatherEntry
import org.premiumapp.arforecast.data.db.entity.WeatherLocation

data class CurrentWeatherResponse(
    val location: WeatherLocation,
    val current: CurrentWeatherEntry
)