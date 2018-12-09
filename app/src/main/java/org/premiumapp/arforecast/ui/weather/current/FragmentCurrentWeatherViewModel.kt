package org.premiumapp.arforecast.ui.weather.current

import androidx.lifecycle.ViewModel
import org.premiumapp.arforecast.data.provider.UnitProvider
import org.premiumapp.arforecast.data.repository.RepositoryForecast
import org.premiumapp.arforecast.internal.UnitSystem
import org.premiumapp.arforecast.internal.lazyDeferred

class FragmentCurrentWeatherViewModel(
    private val forecastRepository: RepositoryForecast,
    unitProvider: UnitProvider)
    : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
