package com.korol.myweather.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.korol.myweather.api.*
import com.korol.myweather.repository.ListItem
import com.korol.myweather.ui.city.CityRepository
import com.korol.myweather.ui.details.DetailsRepository
import com.korol.myweather.ui.details.WeatherInterpretationCodes
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class ViewCityDataBaseManager(context: Context) : CityRepository, DetailsRepository {
    val viewedCityDBHelper = ViewedCityDataBaseHelper(context)
    var db: SQLiteDatabase? = null

    private lateinit var job: Deferred<WeatherInfo>

    var mService: RetrofitServices = Common.retrofitService

    override fun insertToDb(
        cityName: String, nameRegion: String, nameLat: String,
        nameLon: String, currentTemperature: String,
        weatherVariable: String, windSpeed: String, hourTemperature: String,
        sixHourTemperature: String, twelveHourTemperature: String,
        twentyFourHourTemperature: String, currentTime: String
    ) {
        db = viewedCityDBHelper.writableDatabase
        val values = ContentValues().apply {
            put(ViewedCityDataBaseNameClass.COLUMN_NAME_CITY, cityName)
            put(ViewedCityDataBaseNameClass.COLUMN_NAME_REGION, nameRegion)
            put(ViewedCityDataBaseNameClass.COLUMN_NAME_LATITUDE, nameLat)
            put(ViewedCityDataBaseNameClass.COLUMN_NAME_LONGITUDE, nameLon)
            put(ViewedCityDataBaseNameClass.COLUMN_CURRENT_TEMPERATURE, currentTemperature)
            put(ViewedCityDataBaseNameClass.COLUMN_WEATHER_VARIABLE, weatherVariable)
            put(ViewedCityDataBaseNameClass.COLUMN_WIND_SPEED, windSpeed)
            put(ViewedCityDataBaseNameClass.COLUMN_HOUR_TEMPERATURE, hourTemperature)
            put(ViewedCityDataBaseNameClass.COLUMN_SIX_HOUR_TEMPERATURE, sixHourTemperature)
            put(ViewedCityDataBaseNameClass.COLUMN_TWELVE_HOUR_TEMPERATURE, twelveHourTemperature)
            put(
                ViewedCityDataBaseNameClass.COLUMN_TWENTY_FOUR_HOUR_TEMPERATURE,
                twentyFourHourTemperature
            )
            put(ViewedCityDataBaseNameClass.COLUMN_CURRENT_TIME, currentTime)
        }
        db?.insert(ViewedCityDataBaseNameClass.TABLE_NAME, null, values)
        viewedCityDBHelper.close()

    }

    override fun updateItem(
        cityName: String, nameRegion: String, nameLat: String,
        nameLon: String, currentTemperature: String,
        weatherVariable: String, windSpeed: String, hourTemperature: String,
        sixHourTemperature: String, twelveHourTemperature: String,
        twentyFourHourTemperature: String, currentTime: String
    ) {
        db = viewedCityDBHelper.writableDatabase
        val selection = ViewedCityDataBaseNameClass.COLUMN_NAME_LATITUDE + "=? AND " +
                ViewedCityDataBaseNameClass.COLUMN_NAME_LONGITUDE + "=?"
        val values = ContentValues().apply {
            put(ViewedCityDataBaseNameClass.COLUMN_NAME_CITY, cityName)
            put(ViewedCityDataBaseNameClass.COLUMN_NAME_REGION, nameRegion)
            put(ViewedCityDataBaseNameClass.COLUMN_NAME_LATITUDE, nameLat)
            put(ViewedCityDataBaseNameClass.COLUMN_NAME_LONGITUDE, nameLon)
            put(ViewedCityDataBaseNameClass.COLUMN_CURRENT_TEMPERATURE, currentTemperature)
            put(ViewedCityDataBaseNameClass.COLUMN_WEATHER_VARIABLE, weatherVariable)
            put(ViewedCityDataBaseNameClass.COLUMN_WIND_SPEED, windSpeed)
            put(ViewedCityDataBaseNameClass.COLUMN_HOUR_TEMPERATURE, hourTemperature)
            put(ViewedCityDataBaseNameClass.COLUMN_SIX_HOUR_TEMPERATURE, sixHourTemperature)
            put(ViewedCityDataBaseNameClass.COLUMN_TWELVE_HOUR_TEMPERATURE, twelveHourTemperature)
            put(
                ViewedCityDataBaseNameClass.COLUMN_TWENTY_FOUR_HOUR_TEMPERATURE,
                twentyFourHourTemperature
            )
            put(ViewedCityDataBaseNameClass.COLUMN_CURRENT_TIME, currentTime)
        }

        val err = db?.update(
            ViewedCityDataBaseNameClass.TABLE_NAME,
            values,
            selection,
            arrayOf(nameLat, nameLon)
        )
        viewedCityDBHelper.close()
        // если обновить не удалось (элемент не найден), то вставляем в таблицу новый элемент
        if (err == 0) insertToDb(
            cityName, nameRegion, nameLat, nameLon, currentTemperature,
            weatherVariable, windSpeed, hourTemperature,
            sixHourTemperature, twelveHourTemperature, twentyFourHourTemperature, currentTime
        )

    }

    override fun removeItemFromRepo(id: String) {
        db = viewedCityDBHelper.writableDatabase
        val selection = BaseColumns._ID + "=?"
        db?.delete(ViewedCityDataBaseNameClass.TABLE_NAME, selection, arrayOf(id))
        viewedCityDBHelper.close()
    }

    override fun findCity(lat: String, lon: String): ViewedCity {
        val item = ViewedCity()
        db = viewedCityDBHelper.writableDatabase

        val selection =
            "${ViewedCityDataBaseNameClass.COLUMN_NAME_LATITUDE} =? AND ${ViewedCityDataBaseNameClass.COLUMN_NAME_LONGITUDE} =?"
        val cursor = db?.query(
            ViewedCityDataBaseNameClass.TABLE_NAME,
            null,
            selection,
            arrayOf(lat, lon),
            null,
            null,
            null
        )

        while (cursor?.moveToNext()!!) {
            val dataCity =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_NAME_CITY))
            val dataRegion =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_NAME_REGION))
            val dataLat =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_NAME_LATITUDE))
            val dataLon =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_NAME_LONGITUDE))
            val dataCurrentTemperature =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_CURRENT_TEMPERATURE))
            val dataWeatherVariable =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_WEATHER_VARIABLE))
            val dataWindSpeed =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_WIND_SPEED))
            val dataHourTemperature =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_HOUR_TEMPERATURE))
            val dataSixHourTemperature =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_SIX_HOUR_TEMPERATURE))
            val dataTwelveHourTemperature =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_TWELVE_HOUR_TEMPERATURE))
            val dataTwentyFourHourTemperature =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_TWENTY_FOUR_HOUR_TEMPERATURE))
            val dataCurrentTime =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_CURRENT_TIME))
            val dataId =
                cursor.getIntOrNull(cursor.getColumnIndexOrThrow(BaseColumns._ID))

            item.nameCity = dataCity!!
            item.nameRegion = dataRegion!!
            item.latitude = dataLat!!
            item.longitude = dataLon!!
            item.currentTemperature = dataCurrentTemperature!!
            item.weatherVariable = dataWeatherVariable!!
            item.windSpeed = dataWindSpeed!!
            item.hourTemperature = dataHourTemperature!!
            item.sixHourTemperature = dataSixHourTemperature!!
            item.twelveHourTemperature = dataTwelveHourTemperature!!
            item.twentyFourHourTemperature = dataTwentyFourHourTemperature!!
            item.currentTime = dataCurrentTime!!
            item.id = dataId!!

        }
        cursor.close()
        viewedCityDBHelper.close()
        return item
    }

    override suspend fun readData(): ArrayList<ViewedCity> = withContext(Dispatchers.IO) {
        val dataList = ArrayList<ViewedCity>()
        db = viewedCityDBHelper.writableDatabase
        val cursor = db?.query(
            ViewedCityDataBaseNameClass.TABLE_NAME, null, null,
            null, null, null, null
        )

        while (cursor?.moveToNext()!!) {
            val dataCity =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_NAME_CITY))
            val dataRegion =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_NAME_REGION))
            val dataLat =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_NAME_LATITUDE))
            val dataLon =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_NAME_LONGITUDE))
            val dataCurrentTemperature =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_CURRENT_TEMPERATURE))
            val dataWeatherVariable =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_WEATHER_VARIABLE))
            val dataWindSpeed =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_WIND_SPEED))
            val dataHourTemperature =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_HOUR_TEMPERATURE))
            val dataSixHourTemperature =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_SIX_HOUR_TEMPERATURE))
            val dataTwelveHourTemperature =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_TWELVE_HOUR_TEMPERATURE))
            val dataTwentyFourHourTemperature =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_TWENTY_FOUR_HOUR_TEMPERATURE))
            val dataCurrentTime =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ViewedCityDataBaseNameClass.COLUMN_CURRENT_TIME))
            val dataId =
                cursor.getIntOrNull(cursor.getColumnIndexOrThrow(BaseColumns._ID))

            val item = ViewedCity()
            item.nameCity = dataCity!!
            item.nameRegion = dataRegion!!
            item.latitude = dataLat!!
            item.longitude = dataLon!!
            item.currentTemperature = dataCurrentTemperature!!
            item.weatherVariable = dataWeatherVariable!!
            item.windSpeed = dataWindSpeed!!
            item.hourTemperature = dataHourTemperature!!
            item.sixHourTemperature = dataSixHourTemperature!!
            item.twelveHourTemperature = dataTwelveHourTemperature!!
            item.twentyFourHourTemperature = dataTwentyFourHourTemperature!!
            item.currentTime = dataCurrentTime!!
            item.id = dataId!!

            dataList.add(item)
        }
        cursor.close()
        viewedCityDBHelper.close()
        return@withContext dataList
    }

    suspend fun getCurrentWeatherFromInternet(
        cityLatitude: String,
        cityLongitude: String
    ): WeatherInfo = withContext(Dispatchers.IO) {

        val DTI = DateTemperatureInto(listOf("error"), listOf("error"))
        val CW = CurrentWeather("error", "error", -1, "error", "error")
        var myWeather = WeatherInfo("error", "error", "error", "error", "error", DTI, CW)

        try {
            val response = mService.getWeather(cityLatitude, cityLongitude).execute()
            myWeather = response.body() as WeatherInfo
        } catch (e: Exception) {
            Log.d("my_tag", "Retrofit don't work")

        }

        return@withContext myWeather
    }

    override suspend fun getCurrentWeather(
        city:ListItem
    ): ViewedCityWithInfo {

        job = CoroutineScope(Dispatchers.IO).async {
            getCurrentWeatherFromInternet(
                city.latitude.replaceFirst(',', '.'),
                city.longitude.replaceFirst(',', '.')
            )
        }
        val myWeather: WeatherInfo = job.await()

        val viewedCityWithInfo = ViewedCityWithInfo()

        if (myWeather.latitude != "error") {
            viewedCityWithInfo.infoAboutData = "info from internet received."
            viewedCityWithInfo.viewedCity.currentTemperature = myWeather.current_weather.temperature
            viewedCityWithInfo.viewedCity.weatherVariable =
                WeatherInterpretationCodes.weatherCodeToWeather(myWeather.current_weather.weathercode)
            viewedCityWithInfo.viewedCity.windSpeed = myWeather.current_weather.windspeed

            val currentTime = myWeather.current_weather.time
            var currentTimeIndex = 0
            myWeather.hourly.time.forEachIndexed { index, item ->
                if (currentTime == item) currentTimeIndex = index
            }
            viewedCityWithInfo.viewedCity.hourTemperature =
                myWeather.hourly.temperature_2m[currentTimeIndex + 1]
            viewedCityWithInfo.viewedCity.sixHourTemperature =
                myWeather.hourly.temperature_2m[currentTimeIndex + 6]
            viewedCityWithInfo.viewedCity.twelveHourTemperature =
                myWeather.hourly.temperature_2m[currentTimeIndex + 12]
            viewedCityWithInfo.viewedCity.twentyFourHourTemperature =
                myWeather.hourly.temperature_2m[currentTimeIndex + 24]

            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd-MM-yy kk:mm:ss", Locale.getDefault())
            val currentDate = formatter.format(time)

            updateItem(
                city.city,
                city.typeRegion,
                city.latitude,
                city.longitude,
                myWeather.current_weather.temperature,
                myWeather.current_weather.weathercode.toString(),
                myWeather.current_weather.windspeed,
                myWeather.hourly.temperature_2m[currentTimeIndex + 1],
                myWeather.hourly.temperature_2m[currentTimeIndex + 6],
                myWeather.hourly.temperature_2m[currentTimeIndex + 12],
                myWeather.hourly.temperature_2m[currentTimeIndex + 24],
                currentDate
            )
        } else {
            viewedCityWithInfo.infoAboutData = "information from the Internet is not received."
            var item = ViewedCity()
            item = findCity(city.latitude, city.longitude)

            if (item.nameCity != "empty") {
                viewedCityWithInfo.infoAboutData =
                    "Information from the Internet is not received. Information retrieved from the database on ${item.currentTime}"
                viewedCityWithInfo.viewedCity.currentTemperature = item.currentTemperature
                viewedCityWithInfo.viewedCity.weatherVariable =
                    WeatherInterpretationCodes.weatherCodeToWeather(item.weatherVariable.toInt())
                viewedCityWithInfo.viewedCity.windSpeed = item.windSpeed
                viewedCityWithInfo.viewedCity.hourTemperature = item.hourTemperature
                viewedCityWithInfo.viewedCity.sixHourTemperature = item.sixHourTemperature
                viewedCityWithInfo.viewedCity.twelveHourTemperature = item.twelveHourTemperature
                viewedCityWithInfo.viewedCity.twentyFourHourTemperature =
                    item.twentyFourHourTemperature

            } else {
                viewedCityWithInfo.infoAboutData =
                    "Information from the Internet is not received and missing from the database."
            }
        }
        return viewedCityWithInfo
    }

}