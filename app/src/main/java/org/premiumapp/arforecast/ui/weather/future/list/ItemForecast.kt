package org.premiumapp.arforecast.ui.weather.future.list

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_forecast.*
import org.premiumapp.arforecast.R
import org.premiumapp.arforecast.data.db.uintlocalized.future.MetricSimpleFutureWeatherEntry
import org.premiumapp.arforecast.data.db.uintlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import org.premiumapp.arforecast.internal.glide.GlideApp
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class ItemForecast(
    val weatherEntry: UnitSpecificSimpleFutureWeatherEntry
) : Item() {

    override fun getLayout(): Int = R.layout.item_forecast

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_condition.text = weatherEntry.conditionText
            updateDate()
            updateTemperature()
            updateConditionImage()
        }
    }

    private fun ViewHolder.updateDate() {
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        textView_date.text = weatherEntry.date.format(dtFormatter)
    }

    private fun ViewHolder.updateTemperature() {
        val unitAbbreviation = if (weatherEntry is MetricSimpleFutureWeatherEntry) "°C" else "°F"
        val avgTempr = "${weatherEntry.avgTemperature}$unitAbbreviation"
        textView_temperature.text = avgTempr
    }

    private fun ViewHolder.updateConditionImage() {
        GlideApp.with(this.containerView)
            .load("http://" + weatherEntry.conditionIconUrl)
            .into(imageView_condition_icon)
    }
}