package com.example.bottlerocket.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bottlerocket.R
import com.example.bottlerocket.data.model.Day
import com.example.bottlerocket.databinding.ItemWeeklyLayoutBinding
import com.example.bottlerocket.util.toIcon

class WeatherWeeklyAdapter(private val selectHourly: (Day) -> Unit) : ListAdapter<Day,
        WeatherWeeklyAdapter.WeatherWeeklyViewHolder>(WeeklyDiffItem) {

    object WeeklyDiffItem : DiffUtil.ItemCallback<Day>() {
        override fun areItemsTheSame(
            oldItem: Day,
            newItem: Day
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Day,
            newItem: Day
        ): Boolean {
            return oldItem == newItem
        }

    }

    inner class WeatherWeeklyViewHolder(private val binding: ItemWeeklyLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(dataItem: Day, selectHourly: (Day) -> Unit) {
            binding.itemWeeklyTemp.text = binding.root.context.getString(
                R.string.temp_indicator,
                dataItem.low
            )
            binding.itemWeeklyDay.text = dataItem.dayOfTheWeek.toDay()
            binding.itemWeeklyIcon.setImageDrawable(
                dataItem.weatherType.toIcon(binding.root.context.resources)
            )
            binding.root.setOnClickListener {
                selectHourly(dataItem)
            }
        }

        private fun Int.toDay() = when (this) {
            0 -> "Mon"
            1 -> "Tue"
            2 -> "Wed"
            3 -> "Thu"
            4 -> "Fri"
            5 -> "Sat"
            6 -> "Sun"
            else -> throw Exception("Incorrect day of the week")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherWeeklyViewHolder {
        return WeatherWeeklyViewHolder(
            ItemWeeklyLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherWeeklyViewHolder, position: Int) {
        holder.onBind(currentList[position], selectHourly)
    }
}