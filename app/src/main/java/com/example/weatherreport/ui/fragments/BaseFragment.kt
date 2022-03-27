package com.example.weatherreport.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherreport.R
import com.example.weatherreport.ui.adapters.PageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseFragment : Fragment(R.layout.fragment_base) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fragmentsAdapter = PageAdapter(this)
        val viewPager = view.findViewById<ViewPager2>(R.id.vp2Pager)
        viewPager.adapter = fragmentsAdapter
    }
}
