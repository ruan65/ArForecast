package org.premiumapp.arforecast.data.response

data class CurrentWeatherResponse(
    val location: Location,
    val current: CurrentWeatherEntry
)