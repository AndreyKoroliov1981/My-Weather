package com.korol.myweather.ui.details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.korol.myweather.db.ViewCityDataBaseManager

class DetailsViewModelFactory(
    val detailDataBase: DetailDataBase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(
            detailDataBase = detailDataBase
        ) as T
    }
}