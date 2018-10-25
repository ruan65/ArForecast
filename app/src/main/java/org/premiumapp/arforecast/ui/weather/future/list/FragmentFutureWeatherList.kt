package org.premiumapp.arforecast.ui.weather.future.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.premiumapp.arforecast.R

class FragmentFutureWeatherList : Fragment() {

    companion object {
        fun newInstance() = FragmentFutureWeatherList()
    }

    private lateinit var viewModel: FragmentFutureWeatherListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_future_weather_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentFutureWeatherListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
