package com.korol.myweather.api

data class WeatherInfo(
    val latitude:String,
    val longitude:String,
    val elevation:String,
    val generationtime_ms:String,
    val utc_offset_seconds:String,
    val hourly:DateTemperatureInto,
    val current_weather:CurrentWeather
)

data class DateTemperatureInto(
    val time:List<String>,
    val temperature_2m:List<String>
)

data class CurrentWeather(
    val time:String,
    val temperature:String,
    val weathercode:Int,
    val windspeed:String,
    val winddirection:String,

)
