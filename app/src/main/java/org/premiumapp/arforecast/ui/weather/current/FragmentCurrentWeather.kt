package org.premiumapp.arforecast.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.premiumapp.arforecast.R
import org.premiumapp.arforecast.base.ScopedFragment
import org.premiumapp.arforecast.data.db.uintlocalized.UnitSpecificCurrentWeatherEntry
import org.premiumapp.arforecast.internal.Cv
import org.premiumapp.arforecast.internal.glide.GlideApp

class FragmentCurrentWeather : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val vmFactory: CurrentWeatherVmFactory by instance()

    private lateinit var viewModelCurrent: FragmentCurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelCurrent = ViewModelProviders.of(this, vmFactory)
            .get(FragmentCurrentWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {

        val currentWeather = viewModelCurrent.weather.await()

        val weatherLocation = viewModelCurrent.weatherLocation.await()

        weatherLocation.observe(this@FragmentCurrentWeather, Observer { location ->
            if (null == location) return@Observer
            updateLocation(location = location.name)
        })

        currentWeather.observe(this@FragmentCurrentWeather, Observer {

            if (null == it) return@Observer

            group_loading.visibility = View.GONE

            updateDateToToday()
            updateTemperature(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText, it.conditionIconUrl)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)
            updateCondition(it.conditionText, it.conditionIconUrl)
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String) =
        if (viewModelCurrent.isMetric) metric else imperial


    private fun updatePrecipitation(precipitationVolume: Double) {

        val unit = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Precipitation: $precipitationVolume $unit"
    }

    private fun updateCondition(conditionText: String, conditionIconUrl: String) {
        textView_condition.text = conditionText
        GlideApp.with(this)
            .load("http:$conditionIconUrl")
            .into(imageView_condition_icon)
    }

    private fun updateTemperature(temperature: Double, feelsLikeTemperature: Double) {
        val unit = chooseLocalizedUnitAbbreviation(Cv.CELCIUS, Cv.FARENHEIGHT)
        textView_temperature.text = "$temperature $unit"
        textView_feels_like_temperature.text = "Feels like $feelsLikeTemperature $unit"
    }

    private fun updateLocation(location: String) {
        (activity as AppCompatActivity).supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as AppCompatActivity).supportActionBar?.subtitle = "Today"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation(
            Cv.KM_PER_HOUR,
            Cv.MILES_PER_HOUR
        )
        textView_wind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation(Cv.KM, Cv.MILES)
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }
}
