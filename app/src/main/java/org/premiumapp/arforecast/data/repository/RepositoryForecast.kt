package org.premiumapp.arforecast.data.repository

import androidx.lifecycle.LiveData
import org.premiumapp.arforecast.data.db.entity.WeatherLocation
import org.premiumapp.arforecast.data.db.uintlocalized.UnitSpecificCurrentWeatherEntry

interface RepositoryForecast {

    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}