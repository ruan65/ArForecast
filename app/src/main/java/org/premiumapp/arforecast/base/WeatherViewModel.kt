package org.premiumapp.arforecast.base

import androidx.lifecycle.ViewModel;
import org.premiumapp.arforecast.data.provider.UnitProvider
import org.premiumapp.arforecast.data.repository.RepositoryForecast
import org.premiumapp.arforecast.internal.UnitSystem
import org.premiumapp.arforecast.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: RepositoryForecast,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}
