package org.premiumapp.arforecast.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.premiumapp.arforecast.R

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
    }

}
