package org.premiumapp.arforecast.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.premiumapp.arforecast.data.db.CurrentWeatherDao
import org.premiumapp.arforecast.data.db.DaoWeatherLocation
import org.premiumapp.arforecast.data.db.entity.WeatherLocation
import org.premiumapp.arforecast.data.db.uintlocalized.current.UnitSpecificCurrentWeatherEntry
import org.premiumapp.arforecast.data.network.WeatherNetworkDataSource
import org.premiumapp.arforecast.data.network.response.current.CurrentWeatherResponse
import org.premiumapp.arforecast.data.provider.LocationProvider
import org.threeten.bp.ZonedDateTime
import java.util.*

class RepositoryForecastImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val daoWeatherLocation: DaoWeatherLocation,
    private val dataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
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

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> =
        withContext(Dispatchers.IO) {
            daoWeatherLocation.getLocation()
        }

    private suspend fun initWeatherData() {

        val lastLocation = daoWeatherLocation.getLocationNonLive()

        if (lastLocation == null || locationProvider.hasLocationChanged(lastLocation)) {
            fetchCurrentWeather()
            return
        }

        if (isFetchCurrentWeatherNeeded(lastLocation.zonedDateTime)) {
            fetchCurrentWeather()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.current)
            daoWeatherLocation.upsert(fetchedWeather.location)
        }
    }

    private suspend fun fetchCurrentWeather() {
        dataSource.fetchCurrentWeather(locationProvider.getPreferredLocation(), Locale.getDefault().language)
    }

    private fun isFetchCurrentWeatherNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}