package com.example.weatherreport.ui.fragments.listeners

import android.widget.SearchView

class OnSearchListener(
    private val shift: (name: String) -> Unit
) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) {
            shift.invoke(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?) = false
}