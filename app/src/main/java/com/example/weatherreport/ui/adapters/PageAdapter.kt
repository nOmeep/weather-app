package com.example.weatherreport.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weatherreport.ui.fragments.MainFragment
import com.example.weatherreport.ui.fragments.SearchFragment

class PageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) MainFragment() else SearchFragment()
    }
}