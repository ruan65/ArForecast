package org.premiumapp.arforecast

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import org.premiumapp.arforecast.data.db.ForecastDb
import org.premiumapp.arforecast.data.network.*
import org.premiumapp.arforecast.data.provider.LocationProvider
import org.premiumapp.arforecast.data.provider.LocationProviderImpl
import org.premiumapp.arforecast.data.provider.UnitProvider
import org.premiumapp.arforecast.data.provider.UnitProviderImpl
import org.premiumapp.arforecast.data.repository.RepositoryForecast
import org.premiumapp.arforecast.data.repository.RepositoryForecastImpl
import org.premiumapp.arforecast.ui.weather.current.FactoryCurrentWeatherViewModel
import org.premiumapp.arforecast.ui.weather.future.list.FactoryForecastViewModel

class ThisApp : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {

        import(androidModule(this@ThisApp))

        bind() from singleton { ForecastDb(instance()) }
        bind() from singleton { instance<ForecastDb>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDb>().futureWeatherDao() }
        bind() from singleton { instance<ForecastDb>().weatherLocationDao() }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }

        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<RepositoryForecast>() with singleton { RepositoryForecastImpl(instance(), instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { FactoryCurrentWeatherViewModel(instance(), instance()) }
        bind() from provider { FactoryForecastViewModel(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}