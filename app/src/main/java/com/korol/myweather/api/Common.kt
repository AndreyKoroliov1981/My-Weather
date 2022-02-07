package com.korol.myweather.api

object Common {
    private val BASE_URL="https://api.open-meteo.com/v1/"
    val retrofitService:RetrofitServices
      get()=RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}