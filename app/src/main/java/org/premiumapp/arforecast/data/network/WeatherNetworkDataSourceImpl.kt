package org.premiumapp.arforecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.premiumapp.arforecast.data.network.response.CurrentWeatherResponse
import org.premiumapp.arforecast.internal.ExceptionNoConnectivity
import java.nio.channels.NoConnectionPendingException

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
}