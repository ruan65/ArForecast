package org.premiumapp.arforecast.ui.weather.future.detailed

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.premiumapp.arforecast.R

class FragmentFutureDetailedWeather : Fragment() {

    companion object {
        fun newInstance() = FragmentFutureDetailedWeather()
    }

    private lateinit var viewModelFutureDetailedWeather: ViewModelFutureDetailedWeather

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_future_detailed_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFutureDetailedWeather = ViewModelProviders.of(this).get(ViewModelFutureDetailedWeather::class.java)
        // TODO: Use the ViewModel
    }

}
