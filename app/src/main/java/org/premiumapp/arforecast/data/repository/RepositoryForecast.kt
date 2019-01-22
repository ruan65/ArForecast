package org.premiumapp.arforecast.data.repository

import androidx.lifecycle.LiveData
import org.premiumapp.arforecast.data.db.entity.WeatherLocation
import org.premiumapp.arforecast.data.db.uintlocalized.current.UnitSpecificCurrentWeatherEntry
import org.premiumapp.arforecast.data.db.uintlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

interface RepositoryForecast {

    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getForecast(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>

    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}