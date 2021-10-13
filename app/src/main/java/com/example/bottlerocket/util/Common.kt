package com.example.bottlerocket.util

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.example.bottlerocket.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.toIcon(resources: Resources): Drawable? {
    return when(this){
        "sunny" -> ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_icon_weather_active_ic_sunny_active,
            null)
        "cloudy" -> ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_icon_weather_active_ic_cloudy_active,
            null)
        "heavyRain" -> ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_icon_weather_active_ic_heavy_rain_active,
            null)
        "lightRain" -> ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_icon_weather_active_ic_light_rain_active,
            null)
        "snowSleet" -> ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_icon_weather_active_ic_snow_sleet_active,
            null)
        else-> ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_icon_weather_active_ic_partly_cloudy_active,
            null
        )
    }
}

fun FragmentActivity.showLoading(isLoading: Boolean){
    val view = findViewById<ProgressBar>(R.id.progress_bar)
    if (isLoading)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

fun FragmentActivity.showError(errorMessage: String){
    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
}

fun String.getDate(): String {
    val date = Date()
    val requiredFormat: DateFormat = SimpleDateFormat("EEE mm/dd/yyyy  hh:mm a")
    requiredFormat.timeZone = TimeZone.getTimeZone(this)
    return requiredFormat.format(date).uppercase(Locale.getDefault())
}

fun Int.getTime(context: Context): String{
    return when (this) {
        in 0..12 -> context.getString(R.string.time_indicator_am, this)
        else -> context.getString(R.string.time_indicator_am, this)
    }
}
