package com.example.weatherreport.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherreport.R
import com.example.weatherreport.data.api.items.WeatherItem
import com.example.weatherreport.databinding.FragmentSearchBinding
import com.example.weatherreport.ui.adapters.CachedItemsAdapter
import com.example.weatherreport.ui.fragments.listeners.ItemSwipeListener
import com.example.weatherreport.ui.fragments.listeners.OnSearchListener
import com.example.weatherreport.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<WeatherViewModel>()

    private val searchedItemsAdapter =
        CachedItemsAdapter { wi: WeatherItem ->
            findNavController().navigate(
                SearchFragmentDirections.fromSearchFragmentToMainFragment(wi.location.name)
            )
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentSearchBinding.bind(view)

        binding.rvCachedItems.adapter = searchedItemsAdapter

        val currentList = mutableListOf<WeatherItem>()

        binding.searchView.setOnQueryTextListener(OnSearchListener { query: String ->
            findNavController().navigate(
                SearchFragmentDirections.fromSearchFragmentToMainFragment(query)
            )
        })

        ItemTouchHelper(
            ItemSwipeListener(
                rvList = currentList,
                rvAdapter = searchedItemsAdapter,
                notify = { nameToDelete: String -> viewModel.removeItemByName(nameToDelete) },
                swipeDirs = ItemTouchHelper.RIGHT
            )
        ).attachToRecyclerView(binding.rvCachedItems)

        viewModel.getAllCachedItems().observe(viewLifecycleOwner) { cachedResource ->
            currentList.apply {
                clear()
                addAll(cachedResource)
            }

            binding.tvEmptyList.isVisible = cachedResource.isEmpty()
            searchedItemsAdapter.submitList(currentList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}