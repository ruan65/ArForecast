package org.premiumapp.arforecast.data.network.response

import org.premiumapp.arforecast.data.db.entity.CurrentWeatherEntry
import org.premiumapp.arforecast.data.db.entity.Location

data class CurrentWeatherResponse(
    val location: Location,
    val current: CurrentWeatherEntry
)