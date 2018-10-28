package org.premiumapp.arforecast.data

data class CurrentWeatherResponse(
    val location: Location,
    val current: CurrentWeatherEntry
)