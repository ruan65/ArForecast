package org.premiumapp.arforecast.ui.weather.current

import androidx.lifecycle.ViewModel
import org.premiumapp.arforecast.data.repository.RepositoryForecast
import org.premiumapp.arforecast.internal.UnitSystem
import org.premiumapp.arforecast.internal.lazyDeferred

class FragmentCurrentWeatherViewModel(private val forecastRepository: RepositoryForecast) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC// get from settings later

    private val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
