package com.korol.myweather.ui.details

import com.korol.myweather.api.WeatherInfo
import com.korol.myweather.db.ViewedCity
import com.korol.myweather.db.ViewedCityWithInfo
import com.korol.myweather.repository.ListItem

interface DetailsRepository {

    fun updateItem(cityName: String, nameRegion: String, nameLat: String,
                   nameLon: String,currentTemperature: String,
                   weatherVariable:String, windSpeed:String,hourTemperature:String,
                   sixHourTemperature:String,twelveHourTemperature:String,
                   twentyFourHourTemperature:String,currentTime:String)

    fun findCity(lat:String,lon:String): ViewedCity


    suspend fun getCurrentWeather(city:ListItem): ViewedCityWithInfo

}