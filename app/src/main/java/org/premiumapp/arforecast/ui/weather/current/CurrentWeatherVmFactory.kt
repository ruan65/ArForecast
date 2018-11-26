package org.premiumapp.arforecast.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.premiumapp.arforecast.data.repository.RepositoryForecast

class CurrentWeatherVmFactory(
    private val forecastRepo: RepositoryForecast
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FragmentCurrentWeatherViewModel(forecastRepo) as T
    }
}