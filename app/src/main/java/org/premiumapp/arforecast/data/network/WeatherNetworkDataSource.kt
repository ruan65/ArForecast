package org.premiumapp.arforecast.data.network

import androidx.lifecycle.LiveData
import org.premiumapp.arforecast.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
}