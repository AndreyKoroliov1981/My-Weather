package com.korol.myweather.ui.city

import com.korol.myweather.db.ViewedCity

interface RVViewedCityOnClickListener {
    fun onClicked(city: ViewedCity)
    fun onClickedBasket(city: ViewedCity)
}
