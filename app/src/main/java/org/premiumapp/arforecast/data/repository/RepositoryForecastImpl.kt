package org.premiumapp.arforecast.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.premiumapp.arforecast.data.db.CurrentWeatherDao
import org.premiumapp.arforecast.data.db.uintlocalized.UnitSpecificCurrentWeatherEntry
import org.premiumapp.arforecast.data.network.WeatherNetworkDataSource
import org.premiumapp.arforecast.data.network.response.CurrentWeatherResponse
import org.threeten.bp.ZonedDateTime

class RepositoryForecastImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    dataSource: WeatherNetworkDataSource
) : RepositoryForecast {

    init {
        dataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> =
        withContext(Dispatchers.IO) {
            initWeatherData()
            if (metric) currentWeatherDao.getWeatherMetric() else currentWeatherDao.getWeatherImperial()
        }

    private suspend fun initWeatherData() {

        if (isFetchCurrentWeatherNeeded())

    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.current)
        }
    }

    private fun isFetchCurrentWeatherNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}