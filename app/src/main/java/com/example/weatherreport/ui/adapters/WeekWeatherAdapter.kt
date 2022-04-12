package com.example.weatherreport.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherreport.data.api.items.WeatherItem.Forecast.Forecastday
import com.example.weatherreport.databinding.ItemWeekDayWeatherBinding
import com.example.weatherreport.util.funs.getDayOfTheWeek

class WeekWeatherAdapter : RecyclerView.Adapter<WeekWeatherAdapter.WeekWeatherViewHolder>() {
    inner class WeekWeatherViewHolder(private val binding: ItemWeekDayWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dayForecast: Forecastday) {
            binding.apply {
                tvWeather.text = "${dayForecast.day.avgtemp_c}"
                tvDayOfTheWeek.text = dayForecast.date.getDayOfTheWeek()

                Glide.with(itemView)
                    .load("https:${dayForecast.day.condition.icon}")
                    .into(ivDayWeatherImage)
            }
        }
    }

    private val asyncDiffer = AsyncListDiffer(this, DIFF_UTIL)

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Forecastday>() {
            override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean =
                oldItem.date == newItem.date

            override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean =
                oldItem == newItem
        }
    }

    fun submitList(list: List<Forecastday>) {
        asyncDiffer.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekWeatherViewHolder {
        val binding =
            ItemWeekDayWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeekWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeekWeatherViewHolder, position: Int) {
        val currentDay = asyncDiffer.currentList[position]
        holder.bind(currentDay)
    }

    override fun getItemCount() = asyncDiffer.currentList.size
}
