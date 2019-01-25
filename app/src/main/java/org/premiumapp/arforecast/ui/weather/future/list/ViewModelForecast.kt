package org.premiumapp.arforecast.ui.weather.future.list

import org.premiumapp.arforecast.data.provider.UnitProvider
import org.premiumapp.arforecast.data.repository.RepositoryForecast
import org.premiumapp.arforecast.internal.lazyDeferred
import org.premiumapp.arforecast.base.WeatherViewModel
import org.threeten.bp.LocalDate

class ViewModelForecast(
    private val forecastRepository: RepositoryForecast,
    unitProvider: UnitProvider)
    : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeferred {
        forecastRepository.getForecast(LocalDate.now(), isMetricUnit)
    }
}
