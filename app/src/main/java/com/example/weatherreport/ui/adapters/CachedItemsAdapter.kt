package com.example.weatherreport.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherreport.data.api.items.WeatherItem
import com.example.weatherreport.databinding.ItemSearchedForBinding
import com.example.weatherreport.ui.adapters.CachedItemsAdapter.CachedWeatherViewHolder

class CachedItemsAdapter(private val shift: (wi: WeatherItem) -> Unit) :
    RecyclerView.Adapter<CachedWeatherViewHolder>() {
    inner class CachedWeatherViewHolder(private val binding: ItemSearchedForBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherItem) {
            binding.apply {
                tvCityName.text = item.location.name
                tvLastUpdated.text = item.current.last_updated
                tvWeatherCached.text = "${item.current.temp_c}"

                Glide.with(itemView)
                    .load("https:${item.current.condition.icon}")
                    .into(ivCachedWeatherImage)
            }
        }
    }

    private val asyncDiffer = AsyncListDiffer(this, DIFF_UTIL)

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<WeatherItem>() {
            override fun areItemsTheSame(oldItem: WeatherItem, newItem: WeatherItem) =
                oldItem.location.name == newItem.location.name

            override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem) =
                oldItem == newItem
        }
    }

    fun submitList(list: List<WeatherItem>) {
        asyncDiffer.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CachedWeatherViewHolder {
        val binding =
            ItemSearchedForBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CachedWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CachedWeatherViewHolder, position: Int) {
        val currentItem = asyncDiffer.currentList[position]
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            shift.invoke(currentItem)
        }
    }

    override fun getItemCount() = asyncDiffer.currentList.size
}
