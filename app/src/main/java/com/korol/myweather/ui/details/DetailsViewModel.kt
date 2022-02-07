package com.korol.myweather.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korol.myweather.api.WeatherInfo
import com.korol.myweather.db.ViewedCity
import com.korol.myweather.db.ViewedCityWithInfo
import com.korol.myweather.repository.ListItem
import com.korol.myweather.ui.details.WeatherInterpretationCodes.weatherCodeToWeather
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class DetailsViewModel(
    private val detailDataBase: DetailDataBase
) : ViewModel() {

    private lateinit var job: Deferred<ViewedCityWithInfo>

    lateinit var city: ListItem

    private val _text = MutableLiveData<String>().apply {
        value = "This is details Fragment"
    }
    val text: LiveData<String> = _text

    var nameCity = MutableLiveData<String>()
    var nameRegion = MutableLiveData<String>()
    var currentTemperature = MutableLiveData<String>()
    var weatherVariable = MutableLiveData<String>()
    var windSpeed = MutableLiveData<String>()
    var hourTemperature = MutableLiveData<String>()
    var sixHourTemperature = MutableLiveData<String>()
    var twelveHourTemperature = MutableLiveData<String>()
    var twentyFourHourTemperature = MutableLiveData<String>()


    fun initialWeatherInfo(cityFrom: ListItem) {
        city = cityFrom
        nameCity.value = city.city
        nameRegion.value = city.typeRegion
        _text.value = "Init api for weather"
       viewModelScope.launch { getCurrentWeather() }

    }

    private suspend fun getCurrentWeather() {
        job = viewModelScope.async {
            detailDataBase.getCurrentWeather(city)
        }
        val myWeather: ViewedCityWithInfo = job.await()

        if (myWeather.infoAboutData== "Information from the Internet is not received and missing from the database.") {
            _text.value=myWeather.infoAboutData
        }
        else {
            _text.value = myWeather.infoAboutData
            currentTemperature.value = myWeather.viewedCity.currentTemperature
            weatherVariable.value =myWeather.viewedCity.weatherVariable

            windSpeed.value = myWeather.viewedCity.windSpeed

            hourTemperature.value = myWeather.viewedCity.hourTemperature
            sixHourTemperature.value = myWeather.viewedCity.sixHourTemperature
            twelveHourTemperature.value = myWeather.viewedCity.twelveHourTemperature
            twentyFourHourTemperature.value = myWeather.viewedCity.twentyFourHourTemperature
        }
    }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }

}