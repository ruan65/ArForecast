package org.premiumapp.arforecast.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.premiumapp.arforecast.data.db.CurrentWeatherDao
import org.premiumapp.arforecast.data.db.DaoWeatherLocation
import org.premiumapp.arforecast.data.db.FutureWeatherDao
import org.premiumapp.arforecast.data.db.entity.WeatherLocation
import org.premiumapp.arforecast.data.db.uintlocalized.current.UnitSpecificCurrentWeatherEntry
import org.premiumapp.arforecast.data.db.uintlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import org.premiumapp.arforecast.data.network.WeatherNetworkDataSource
import org.premiumapp.arforecast.data.network.response.current.CurrentWeatherResponse
import org.premiumapp.arforecast.data.network.response.future.FutureWeatherResponse
import org.premiumapp.arforecast.data.provider.LocationProvider
import org.premiumapp.arforecast.internal.Cv.Companion.FORECAST_DAYS_COUNT
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*

class RepositoryForecastImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val daoWeatherLocation: DaoWeatherLocation,
    private val dataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : RepositoryForecast {

    init {
        dataSource.apply {
            downloadedCurrentWeather.observeForever {
                persistFetchedCurrentWeather(it)
            }
            downloadedForecast.observeForever {
                persistFetchedForecast(it)
            }
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> =
        withContext(Dispatchers.IO) {
            initWeatherData()
            if (metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }

    override suspend fun getForecast(startDate: LocalDate, metric: Boolean)
            : LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> =
        withContext(Dispatchers.IO) {
            initWeatherData()
            if (metric) futureWeatherDao.getSimpleWeatherForecastMetric(startDate)
            else futureWeatherDao.getSimpleWeatherForecastImperial(startDate)
        }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> =
        withContext(Dispatchers.IO) {
            daoWeatherLocation.getLocation()
        }

    private suspend fun initWeatherData() {

        val lastLocation = daoWeatherLocation.getLocationNonLive()

        if (lastLocation == null || locationProvider.hasLocationChanged(lastLocation)) {
            fetchCurrentWeather()
            fetchForecast()
            return
        }

        if (isFetchCurrentWeatherNeeded(lastLocation.zonedDateTime)) {
            fetchCurrentWeather()
        }

        if (isFetchForecastNeeded(lastLocation.zonedDateTime)) {
            fetchForecast()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.current)
            daoWeatherLocation.upsert(fetchedWeather.location)
        }
    }

    private fun persistFetchedForecast(forecastResponse: FutureWeatherResponse?) {

        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }
        GlobalScope.launch(Dispatchers.IO) {
            val forecast = forecastResponse?.futureWeatherEntries?.entries ?: return@launch
            deleteOldForecastData()
            futureWeatherDao.insert(forecast)
            daoWeatherLocation.upsert(forecastResponse.location)
        }
    }

    private suspend fun fetchCurrentWeather() {
        dataSource.fetchCurrentWeather(locationProvider.getPreferredLocation(), Locale.getDefault().language)
    }

    private suspend fun fetchForecast() {
        dataSource.fetchForecast(locationProvider.getPreferredLocation(), Locale.getDefault().language)
    }

    private fun isFetchCurrentWeatherNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private fun isFetchForecastNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val today = LocalDate.now()
        val countFutureWeather = futureWeatherDao.countFutureWeather(today)
        return countFutureWeather < FORECAST_DAYS_COUNT
    }
}