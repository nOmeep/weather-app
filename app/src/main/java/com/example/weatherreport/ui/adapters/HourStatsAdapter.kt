package com.example.weatherreport.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherreport.data.api.items.WeatherItem.Forecast.Forecastday.Hour
import com.example.weatherreport.databinding.ItemHourlyWeatherBinding

class HourStatsAdapter : RecyclerView.Adapter<HourStatsAdapter.HourStatsViewHolder>() {
    inner class HourStatsViewHolder(private val binding: ItemHourlyWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hour: Hour) {
            binding.apply {
                tvHour.text = hour.time
                tvFeelsLike.text = hour.feelslike_c.toString()
                tvTemperature.text = hour.temp_c.toString()
            }
        }
    }

    private val asyncDiffer = AsyncListDiffer(this, DIFF_UTIL)

    companion object {
        private val DIFF_UTIL =
            object : DiffUtil.ItemCallback<Hour>() {
                override fun areItemsTheSame(oldItem: Hour, newItem: Hour): Boolean =
                    oldItem.time == newItem.time

                override fun areContentsTheSame(oldItem: Hour, newItem: Hour): Boolean =
                    oldItem == newItem
            }
    }

    fun submitList(list: List<Hour>) {
        asyncDiffer.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourStatsViewHolder {
        val binding =
            ItemHourlyWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourStatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourStatsViewHolder, position: Int) {
        val currentHour = asyncDiffer.currentList[position]
        holder.bind(currentHour)
    }

    override fun getItemCount() = asyncDiffer.currentList.size
}