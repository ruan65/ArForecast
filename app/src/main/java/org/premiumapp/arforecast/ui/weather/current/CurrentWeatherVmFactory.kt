package org.premiumapp.arforecast.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.premiumapp.arforecast.data.provider.UnitProvider
import org.premiumapp.arforecast.data.repository.RepositoryForecast

class CurrentWeatherVmFactory(
    private val forecastRepo: RepositoryForecast,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelCurrentWeather(forecastRepo, unitProvider) as T
    }
}