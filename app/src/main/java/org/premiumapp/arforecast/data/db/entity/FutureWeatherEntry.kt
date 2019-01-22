package org.premiumapp.arforecast.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.premiumapp.arforecast.data.network.response.future.Astro

@Entity(tableName = "future_weather", indices = [Index(value = ["date"], unique = true)])
data class FutureWeatherEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val astro: Astro,
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    @Embedded
    val day: Day
)