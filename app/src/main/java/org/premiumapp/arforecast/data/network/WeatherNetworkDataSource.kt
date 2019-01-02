package org.premiumapp.arforecast.data.network

import androidx.lifecycle.LiveData
import org.premiumapp.arforecast.data.network.response.current.CurrentWeatherResponse
import org.premiumapp.arforecast.data.network.response.future.FutureWeatherResponse

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedForecast: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )

    suspend fun fetchForecast(
        location: String,
        languageCode: String
    )
}