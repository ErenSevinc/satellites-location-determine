package com.example.satelliteslocationdetermine.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.satelliteslocationdetermine.R
import com.example.satelliteslocationdetermine.data.response.SatelliteListResponseModel
import com.example.satelliteslocationdetermine.databinding.ActivityMainBinding
import com.example.satelliteslocationdetermine.presenter.SatelliteListAdapter
import com.example.satelliteslocationdetermine.presenter.utils.DividerItemDecorator
import com.example.satelliteslocationdetermine.ui.viewModel.SatelliteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatellitesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SatelliteListAdapter
    private val viewModel: SatelliteViewModel by viewModels()
    private lateinit var satelliteList: MutableList<SatelliteListResponseModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSatellites()
        initLayout()
        initObservers()
    }

    private fun initLayout() {
        satelliteList = mutableListOf()

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean =false

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let {
                    if (it.isEmpty()) {
                        adapter.setItems(satelliteList)
                    } else if(it.length >= 3 || it.isDigitsOnly()) {
                        val filteredSatelliteList = satelliteList.filter { item->
                            item.name.contains(it,true)
                        }.toMutableList()
                        adapter.setItems(filteredSatelliteList)
                    }
                }
                return true
            }

        })
    }

    private fun initObservers() {
        viewModel.satelliteListUIState.observe(this) {
            binding.loader.isVisible = it.loading
            if (it.error != null) {
                binding.tvErrorMessage.text = it.error.message
                binding.tvErrorMessage.isVisible = true
            }
            if (it.data != null) {
                binding.tvErrorMessage.isVisible = false
                binding.loader.isVisible = false
                satelliteList = it.data

                val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                adapter = SatelliteListAdapter()
                adapter.setItems(satelliteList)
                binding.recyclerView.layoutManager = layoutManager
                binding.recyclerView.adapter = adapter

                val dividerItemDecorator = DividerItemDecorator(getDrawable(R.drawable.divider)!!)
                binding.recyclerView.addItemDecoration(
                    dividerItemDecorator
                )
            }
        }
    }
}