package com.example.satelliteslocationdetermine.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.satelliteslocationdetermine.R
import com.example.satelliteslocationdetermine.data.response.SatelliteListResponseModel
import com.example.satelliteslocationdetermine.databinding.ItemSatelliteListBinding
import com.example.satelliteslocationdetermine.ui.DetailActivity

class SatelliteListAdapter() :
    RecyclerView.Adapter<SatelliteListAdapter.SatelliteViewHolder>() {

    private var list: MutableList<SatelliteListResponseModel> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newList: MutableList<SatelliteListResponseModel>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatelliteViewHolder {
        val binding =
            ItemSatelliteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SatelliteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SatelliteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SatelliteViewHolder(val binding: ItemSatelliteListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SatelliteListResponseModel) {
            binding.textSateliteName.text = item.name
            if (item.active) {
                binding.textSateliteStatus.text = itemView.context.getString(R.string.text_active)
                binding.textSateliteName.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.black
                    )
                )
                binding.textSateliteStatus.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.black
                    )
                )
                binding.imageStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.green
                    )
                )

                binding.root.setOnClickListener {
                    val intent = DetailActivity.newIntent(itemView.context, item.id, item.name)
                    itemView.context.startActivity(intent)
                }
            } else {
                binding.textSateliteStatus.text = itemView.context.getString(R.string.text_passive)
                binding.textSateliteName.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.gray
                    )
                )
                binding.textSateliteStatus.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.gray
                    )
                )
                binding.imageStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.red
                    )
                )
            }
        }
    }
}