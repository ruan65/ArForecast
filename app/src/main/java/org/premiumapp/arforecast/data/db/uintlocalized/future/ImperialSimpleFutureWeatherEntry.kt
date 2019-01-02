package org.premiumapp.arforecast.data.db.uintlocalized.future

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

data class ImperialSimpleFutureWeatherEntry(
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "avgtempF")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String
) : UnitSpecificSipmpleFutureWeatherEntry