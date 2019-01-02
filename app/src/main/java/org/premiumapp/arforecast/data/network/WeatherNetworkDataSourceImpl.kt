package org.premiumapp.arforecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.premiumapp.arforecast.data.network.response.current.CurrentWeatherResponse
import org.premiumapp.arforecast.data.network.response.future.FutureWeatherResponse
import org.premiumapp.arforecast.internal.Cv.Companion.FORECAST_DAYS_COUNT
import org.premiumapp.arforecast.internal.ExceptionNoConnectivity

class WeatherNetworkDataSourceImpl(
    private val apixuWeatherApiService: ApixuWeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val weatherResponse = apixuWeatherApiService
                .getCurrentWeather(location, languageCode)
                .await()

            _downloadedCurrentWeather.postValue(weatherResponse)
        } catch (exc: ExceptionNoConnectivity) {
            Log.e("mytag", "No internet connection", exc)
        }
    }

    private val _downloadedForecast = MutableLiveData<FutureWeatherResponse>()
    override val downloadedForecast: LiveData<FutureWeatherResponse>
        get() = _downloadedForecast

    override suspend fun fetchForecast(location: String, languageCode: String) {
        try {
            val fetchedForecast = apixuWeatherApiService
                .getForecast(location, FORECAST_DAYS_COUNT, languageCode)
                .await()
            _downloadedForecast.postValue(fetchedForecast)
        } catch (exc: ExceptionNoConnectivity) {
            Log.e("mytag", "No internet connection", exc)
        }
    }
}