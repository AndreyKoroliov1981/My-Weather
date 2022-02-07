package com.korol.myweather.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.korol.myweather.repository.CityDataBase

class SearchViewModelFactory(val searchCity: SearchCity) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(searchCity = searchCity) as T
    }

}