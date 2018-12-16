package org.premiumapp.arforecast.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.premiumapp.arforecast.data.db.entity.CurrentWeatherEntry
import org.premiumapp.arforecast.data.db.entity.WeatherLocation

@Database(
    entities = [
        CurrentWeatherEntry::class,
        WeatherLocation::class
    ],
    version = 1
)
abstract class ForecastDb : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): DaoWeatherLocation

    companion object {
        @Volatile
        private var instance: ForecastDb? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDb(context).also { instance = it }
        }

        private fun buildDb(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ForecastDb::class.java, "forecast.db"
            )
                .build()
    }
}