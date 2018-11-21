package org.premiumapp.arforecast

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.premiumapp.arforecast.data.db.ForecastDb
import org.premiumapp.arforecast.data.network.*
import org.premiumapp.arforecast.data.repository.RepositoryForecast
import org.premiumapp.arforecast.data.repository.RepositoryForecastImpl

class ThisApp : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {

        import(androidModule(this@ThisApp))

        bind() from singleton { ForecastDb(instance()) }
        bind() from singleton { instance<ForecastDb>().currentWeatherDao() }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }

        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<RepositoryForecast>() with singleton { RepositoryForecastImpl(instance(), instance()) }
    }
}