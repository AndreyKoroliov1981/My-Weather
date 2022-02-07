package com.korol.myweather.ui.city

import com.korol.myweather.db.ViewedCity

class City(private val cityRepository: CityRepository) {


    fun removeItemFromRepo(id: String){
        cityRepository.removeItemFromRepo(id)
    }

    suspend fun readData():ArrayList<ViewedCity>{
         return cityRepository.readData()
    }

}