package org.premiumapp.arforecast.data.network.response.future

import com.google.gson.annotations.SerializedName
import org.premiumapp.arforecast.data.db.entity.FutureWeatherEntry

data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)