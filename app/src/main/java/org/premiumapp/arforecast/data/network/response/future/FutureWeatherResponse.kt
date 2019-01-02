package org.premiumapp.arforecast.data.network.response.future

import com.google.gson.annotations.SerializedName
import org.premiumapp.arforecast.data.db.entity.WeatherLocation

data class FutureWeatherResponse(
    val current: Current,
    @SerializedName("forecast")
    val futureWeatherEntries: ForecastDaysContainer,
    val location: WeatherLocation
)