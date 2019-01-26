package org.premiumapp.arforecast.ui.weather.future.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

import org.premiumapp.arforecast.R
import org.premiumapp.arforecast.base.ScopedFragment
import org.premiumapp.arforecast.data.db.entity.WeatherLocation

class FragmentFutureWeatherList : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FactoryForecastViewModel by instance()
    private lateinit var viewModel: ViewModelForecast

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_future_weather_list, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ViewModelForecast::class.java)
        bindUi()
        updateDateToNextWeek()
    }

    private fun bindUi() = launch(Dispatchers.Main) {
        val forecast = viewModel.weather.await()
        val locationLive = viewModel.weatherLocation.await()

        locationLive.observe(this@FragmentFutureWeatherList, Observer { location ->
            if (null == location) return@Observer
            updateLocation(location)
        })
    }

    private fun updateLocation(location: WeatherLocation?) {
        val name = location?.name ?: return
        (activity as AppCompatActivity).supportActionBar?.title = name
    }

    private fun updateDateToNextWeek() {
        (activity as AppCompatActivity).supportActionBar?.subtitle = "Next Week"
    }
}
