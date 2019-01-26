package org.premiumapp.arforecast.ui.weather.future.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_future_weather_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.premiumapp.arforecast.R
import org.premiumapp.arforecast.base.ScopedFragment
import org.premiumapp.arforecast.data.db.uintlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import org.premiumapp.arforecast.internal.setActionBarSubTitle
import org.premiumapp.arforecast.internal.setActionBarTitle

class FragmentForecast : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FactoryForecastViewModel by instance()
    private lateinit var viewModel: ViewModelForecast

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_future_weather_list, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setActionBarTitle("")
        setActionBarSubTitle("Next Week")
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ViewModelForecast::class.java)
        bindUi()
    }

    private fun bindUi() = launch(Dispatchers.Main) {
        val forecast = viewModel.weather.await()
        val locationLive = viewModel.weatherLocation.await()

        locationLive.observe(this@FragmentForecast, Observer { location ->
            if (null == location) return@Observer
            setActionBarTitle(location.name ?: "")
        })

        forecast.observe(this@FragmentForecast, Observer {
            if (null == it) return@Observer
            group_loading.visibility = View.GONE
            initRecyclerView(it.toItemsForecast())
        })
    }

    private fun initRecyclerView(items: List<ItemForecast>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FragmentForecast.context)
            val groupAdapter = GroupAdapter<ViewHolder>().apply { addAll(items) }
            adapter = groupAdapter
            groupAdapter.setOnItemClickListener { _, _ ->
                Toast.makeText(this@FragmentForecast.context, "Clicked....", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun List<UnitSpecificSimpleFutureWeatherEntry>.toItemsForecast(): List<ItemForecast> =
        this.map { ItemForecast(it) }
}
