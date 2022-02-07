package com.korol.myweather.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.korol.myweather.databinding.RcCityItemBinding
import com.korol.myweather.repository.ListItem

class RvAdapter(
    var citysList: ArrayList<ListItem>,
    private val onClickListener: RVOnclickListener
) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RcCityItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapter.ViewHolder {
        val binding =
            RcCityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(citysList[position]) {
                binding.tvCity.text = this.city
                binding.tvRegion.text = this.typeRegion
            }
            binding.container.setOnClickListener {
                onClickListener.onClicked(citysList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return citysList.size
    }

}
