package com.korol.myweather.ui.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.korol.myweather.databinding.RcViewedCityItemBinding
import com.korol.myweather.db.ViewedCity


class RvViewedCityAdapter (
    var citysList: ArrayList<ViewedCity>,
    private val onClickListener: RVViewedCityOnClickListener
    ) : RecyclerView.Adapter<RvViewedCityAdapter.ViewHolder>() {
        inner class ViewHolder(val binding: RcViewedCityItemBinding) : RecyclerView.ViewHolder(binding.root)


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewedCityAdapter.ViewHolder {
            val binding =RcViewedCityItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder) {
                with(citysList[position]) {
                    binding.nameCity.text = this.nameCity
                    binding.nameRegion.text = this.nameRegion
                    binding.saveTime.text=this.currentTime
                }

                binding.basket.setOnClickListener {
                    onClickListener.onClickedBasket(citysList[position])
                }

                binding.containerItem.setOnClickListener {
                        onClickListener.onClicked(citysList[position])
                }
            }
        }

        override fun getItemCount(): Int {
            return citysList.size
        }

    }