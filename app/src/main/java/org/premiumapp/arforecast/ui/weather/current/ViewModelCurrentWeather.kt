package org.premiumapp.arforecast.ui.weather.current

import org.premiumapp.arforecast.data.provider.UnitProvider
import org.premiumapp.arforecast.data.repository.RepositoryForecast
import org.premiumapp.arforecast.internal.lazyDeferred
import org.premiumapp.arforecast.base.WeatherViewModel

class ViewModelCurrentWeather(
    private val forecastRepository: RepositoryForecast,
    unitProvider: UnitProvider)
    : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetricUnit)
    }
}
