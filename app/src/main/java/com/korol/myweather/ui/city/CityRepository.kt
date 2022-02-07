package com.korol.myweather.ui.city

import com.korol.myweather.db.ViewedCity

interface CityRepository {

    fun insertToDb(cityName: String, nameRegion: String, nameLat: String,
                   nameLon: String,currentTemperature: String,
                   weatherVariable:String, windSpeed:String,hourTemperature:String,
                   sixHourTemperature:String,twelveHourTemperature:String,
                   twentyFourHourTemperature:String,currentTime:String)

    fun updateItem(cityName: String, nameRegion: String, nameLat: String,
                   nameLon: String,currentTemperature: String,
                   weatherVariable:String, windSpeed:String,hourTemperature:String,
                   sixHourTemperature:String,twelveHourTemperature:String,
                   twentyFourHourTemperature:String,currentTime:String)

    fun removeItemFromRepo(id: String)

    fun findCity(lat:String,lon:String): ViewedCity

    suspend fun readData():ArrayList<ViewedCity>


}