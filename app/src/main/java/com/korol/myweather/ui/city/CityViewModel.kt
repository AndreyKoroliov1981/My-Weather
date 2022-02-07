package com.korol.myweather.ui.city

import android.app.Application
import androidx.lifecycle.*
import com.korol.myweather.db.ViewCityDataBaseManager
import com.korol.myweather.db.ViewedCity
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CityViewModel(private val city:City) : ViewModel() {

    private var _viewedCitys=MutableLiveData<ArrayList<ViewedCity>>()
    var viewedCitys:LiveData<ArrayList<ViewedCity>> =_viewedCitys
    private var job: Job?=null

    init{
        fillAdapter()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Database of viewed cities is empty"
    }
    val text: LiveData<String> = _text


    private fun fillAdapter() {
        job?.cancel()
        job= viewModelScope.launch{
            _viewedCitys.postValue(city.readData())
        }
    }

    fun deleteCity(cityDel:ViewedCity){
        city.removeItemFromRepo(cityDel.id.toString())
        fillAdapter()
    }

}