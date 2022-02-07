package com.korol.myweather.repository

import android.provider.BaseColumns

object MyCityDataBaseNameClass {
    const val TABLE_NAME = "citytables"
    const val COLUMN_NAME_CITY = "City"
    const val COLUMN_NAME_TYPE_REGION = "typeRegion"
    const val COLUMN_NAME_REGION = "Region"
    const val COLUMN_NAME_LATITUDE = "Latitude"
    const val COLUMN_NAME_LONGITUDE = "Longitude"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "CityTables.db"


    const val SQL_CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_CITY TEXT," +
                "$COLUMN_NAME_TYPE_REGION TEXT," +
                "$COLUMN_NAME_REGION TEXT," +
                "$COLUMN_NAME_LATITUDE TEXT," +
                "$COLUMN_NAME_LONGITUDE TEXT)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}