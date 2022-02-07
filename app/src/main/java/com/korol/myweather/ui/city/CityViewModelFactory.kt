package com.korol.myweather.ui.city

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.korol.myweather.db.ViewCityDataBaseManager

class CityViewModelFactory(val city: City): ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return CityViewModel(city=city) as T
    }
}