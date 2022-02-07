package com.korol.myweather.ui.details

import com.korol.myweather.api.WeatherInfo
import com.korol.myweather.db.ViewedCity
import com.korol.myweather.db.ViewedCityWithInfo
import com.korol.myweather.repository.ListItem

class DetailDataBase(private val detailsRepository: DetailsRepository) {

    fun updateItem(cityName: String, nameRegion: String, nameLat: String,
                   nameLon: String,currentTemperature: String,
                   weatherVariable:String, windSpeed:String,hourTemperature:String,
                   sixHourTemperature:String,twelveHourTemperature:String,
                   twentyFourHourTemperature:String,currentTime:String){
        detailsRepository.updateItem(cityName, nameRegion, nameLat,
            nameLon,currentTemperature,
            weatherVariable, windSpeed,hourTemperature,
            sixHourTemperature,twelveHourTemperature,
            twentyFourHourTemperature,currentTime)

    }

    fun findCity(lat:String,lon:String): ViewedCity{
        return detailsRepository.findCity(lat,lon)
    }

    suspend fun getCurrentWeather(city: ListItem): ViewedCityWithInfo {
        return detailsRepository.getCurrentWeather(city)
    }

}