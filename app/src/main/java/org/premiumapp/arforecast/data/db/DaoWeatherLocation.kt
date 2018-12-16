package org.premiumapp.arforecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.premiumapp.arforecast.data.db.entity.WEATHER_LOCATION_ID
import org.premiumapp.arforecast.data.db.entity.WeatherLocation

@Dao
interface DaoWeatherLocation {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherLocation)

    @Query("select * from weather_location where id = $WEATHER_LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>
}