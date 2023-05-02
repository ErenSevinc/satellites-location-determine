package com.example.satelliteslocationdetermine.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.satelliteslocationdetermine.databinding.ActivityDetailBinding
import com.example.satelliteslocationdetermine.ui.viewModel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: Long = intent.getLongExtra(DETAIL_ID, -1)
        val name: String? = intent.getStringExtra(SATELLITE_NAME)

        viewModel.getSatelliteDetail(id)
        viewModel.getPosition(id)
        initObservers(id,name)
    }

    private fun initObservers(id: Long, name: String?) {
        viewModel.satelliteDetailUIState.observe(this) {
            binding.loader.isVisible = it.loading
            if (it.error != null) {
                binding.tvErrorMessage.text = it.error.message
                binding.tvErrorMessage.isVisible = true
            }
            if (it.data != null) {
                it.data.let { item ->
                    binding.tvErrorMessage.isVisible = false
                    binding.loader.isVisible = false

                    binding.tvName.text = name
                    binding.tvDate.text = item.firstFlight
                    binding.tvCost.text = item.costPerLaunch.toString()
                    binding.tvHeight.text = "${item.height}/${item.mass}"
                }
            }
        }
        viewModel.positionUIState.observe(this) {
            binding.loader.isVisible = it.loading
            if (it.error != null) {
                binding.tvErrorMessage.text = it.error.toString()
                binding.tvErrorMessage.isVisible = true
            }
            if (it.data != null) {
                it.data.let {item ->
                    binding.tvPosition.text = "(${item.posX} , ${item.posY})"
                }
            }
        }
    }

    companion object {
        const val DETAIL_ID = "detailId"
        const val SATELLITE_NAME = "name"

        fun newIntent(context: Context, id: Long, name:String): Intent {
            return Intent(context, DetailActivity::class.java)
                .putExtra(DETAIL_ID, id)
                .putExtra(SATELLITE_NAME, name)
        }
    }
}