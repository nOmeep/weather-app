package com.example.weatherreport.ui.fragments.listeners

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherreport.data.api.items.WeatherItem
import com.example.weatherreport.ui.adapters.CachedItemsAdapter

class ItemSwipeListener(
    private val rvList: MutableList<WeatherItem>,
    private val rvAdapter: CachedItemsAdapter,
    private val notify: (str: String) -> Unit,
    swipeDirs: Int,
    dragDirs: Int = 0
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition

        notify.invoke(rvList[position].location.name)
        rvList.removeAt(position)
        rvAdapter.notifyItemRemoved(position)
    }
}