package com.example.bottlerocket.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bottlerocket.R
import com.example.bottlerocket.data.model.HourlyWeather
import com.example.bottlerocket.databinding.ItemDailyLayoutBinding
import com.example.bottlerocket.util.getTime
import com.example.bottlerocket.util.toIcon

class WeatherDailyAdapter : ListAdapter<HourlyWeather,
        WeatherDailyAdapter.WeatherDailyViewHolder>(DailyDiffItem) {

    object DailyDiffItem : DiffUtil.ItemCallback<HourlyWeather>() {
        override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
            return oldItem == newItem
        }

    }

    inner class WeatherDailyViewHolder(private val binding: ItemDailyLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(dataItem: HourlyWeather) {
            binding.itemDailyHumidity.text = binding.root.context.getString(
                R.string.measure_percentage,
                dataItem.humidity
            )
            binding.itemDailyRain.text = binding.root.context.getString(
                R.string.measure_percentage,
                dataItem.rainChance
            )
            binding.itemDailyTemp.text = binding.root.context.getString(
                R.string.temp_indicator,
                dataItem.temperature
            )
            binding.itemDailyTime.text = dataItem.hour.getTime(binding.root.context)
            binding.itemDailyWind.text = binding.root.context.getString(
                R.string.wind_decimals,
                dataItem.windSpeed
            )
            binding.itemDailyIcon.setImageDrawable(
                dataItem.weatherType.toIcon(binding.root.resources)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDailyViewHolder {
        return WeatherDailyViewHolder(
            ItemDailyLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherDailyViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }
}