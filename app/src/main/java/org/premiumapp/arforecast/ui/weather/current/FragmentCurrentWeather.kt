package org.premiumapp.arforecast.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import org.premiumapp.arforecast.R
import org.premiumapp.arforecast.data.ApixuWeatherApiService

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
        // TODO: Use the ViewModel

        val apiService = ApixuWeatherApiService()

        GlobalScope.launch(Dispatchers.Main) {
            val weatherResponse = apiService.getCurrentWeather("Moscow").await()

            tv_current_weather.text = weatherResponse.toString()

        }
    }

}
