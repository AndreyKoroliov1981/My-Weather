package com.korol.myweather.api

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {

    @GET("forecast?&hourly=temperature_2m&current_weather=true&windspeed_unit=ms")
    fun getWeather(
        @Query("latitude") lat: String,
        @Query("longitude") lon: String
    ): Call<WeatherInfo>

}