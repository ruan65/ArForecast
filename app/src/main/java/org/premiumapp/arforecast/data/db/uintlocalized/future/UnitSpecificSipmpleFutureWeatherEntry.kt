package org.premiumapp.arforecast.data.db.uintlocalized.future

import org.threeten.bp.LocalDate

interface UnitSpecificSipmpleFutureWeatherEntry {
    val date: LocalDate
    val avgTemperature: Double
    val conditionText: String
    val conditionIconUrl: String
}