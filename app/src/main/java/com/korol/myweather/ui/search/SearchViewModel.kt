package com.korol.myweather.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korol.myweather.repository.ListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(private val searchCity:SearchCity) : ViewModel() {

    private var job: Job?=null
    private var _citys=MutableLiveData<ArrayList<ListItem>>()
    var citys:LiveData<ArrayList<ListItem>> =_citys

    init{
        fillAdapter("")
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is search Fragment, CitiBase read."
    }
    val text: LiveData<String> = _text


    fun fillAdapter(text:String) {
        job?.cancel()
        job=viewModelScope.launch{
            _citys.postValue(searchCity.readData(text))
        }
    }


}