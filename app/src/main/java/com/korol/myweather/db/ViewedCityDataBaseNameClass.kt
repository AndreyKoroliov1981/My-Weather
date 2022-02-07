package com.korol.myweather.db

import android.provider.BaseColumns

object ViewedCityDataBaseNameClass {

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "viewedCity.db"

    const val TABLE_NAME = "viewed_city_table"
    const val COLUMN_NAME_CITY = "city"
    const val COLUMN_NAME_REGION = "region"
    const val COLUMN_NAME_LATITUDE = "Latitude"
    const val COLUMN_NAME_LONGITUDE = "Longitude"
    const val COLUMN_CURRENT_TEMPERATURE = "current_temperature"
    const val COLUMN_WEATHER_VARIABLE = "weather_variable"
    const val COLUMN_WIND_SPEED = "wind_speed"
    const val COLUMN_HOUR_TEMPERATURE = "hour_temperature"
    const val COLUMN_SIX_HOUR_TEMPERATURE = "six_hour_temperature"
    const val COLUMN_TWELVE_HOUR_TEMPERATURE = "twelve_hour_temperature"
    const val COLUMN_TWENTY_FOUR_HOUR_TEMPERATURE = "twenty_four_hour_temperature"
    const val COLUMN_CURRENT_TIME = "current_time"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_CITY TEXT," +
            "$COLUMN_NAME_REGION TEXT," +
            "$COLUMN_NAME_LATITUDE TEXT,"+
            "$COLUMN_NAME_LONGITUDE TEXT,"+
            "$COLUMN_CURRENT_TEMPERATURE TEXT," +
            "$COLUMN_WEATHER_VARIABLE TEXT," +
            "$COLUMN_WIND_SPEED TEXT," +
            "$COLUMN_HOUR_TEMPERATURE TEXT," +
            "$COLUMN_SIX_HOUR_TEMPERATURE TEXT," +
            "$COLUMN_TWELVE_HOUR_TEMPERATURE TEXT," +
            "$COLUMN_TWENTY_FOUR_HOUR_TEMPERATURE TEXT," +
            "$COLUMN_CURRENT_TIME TEXT)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}