package org.premiumapp.arforecast.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.premiumapp.arforecast.R
import org.premiumapp.arforecast.data.network.ApixuWeatherApiService
import org.premiumapp.arforecast.data.network.ConnectivityInterceptorImpl
import org.premiumapp.arforecast.data.network.WeatherNetworkDataSourceImpl

class FragmentCurrentWeather : Fragment() {

    companion object {
        fun newInstance() = FragmentCurrentWeather()
    }

    private lateinit var viewModelCurrent: FragmentCurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelCurrent = ViewModelProviders.of(this).get(FragmentCurrentWeatherViewModel::class.java)

        val apiService = ApixuWeatherApiService(ConnectivityInterceptorImpl(context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)

        weatherNetworkDataSource.downloadedCurrentWeather.observe(this,
            Observer { weatherResponse ->
                tv_current_weather.text = weatherResponse.toString()
            })

        GlobalScope.launch(Dispatchers.Main) {
            weatherNetworkDataSource.fetchCurrentWeather("Moscow", "en")
        }
    }
}
