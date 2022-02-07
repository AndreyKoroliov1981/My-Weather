package com.korol.myweather.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.korol.myweather.db.ViewedCityDataBaseNameClass.CREATE_TABLE
import com.korol.myweather.db.ViewedCityDataBaseNameClass.DATABASE_NAME
import com.korol.myweather.db.ViewedCityDataBaseNameClass.DATABASE_VERSION
import com.korol.myweather.db.ViewedCityDataBaseNameClass.SQL_DELETE_TABLE

class ViewedCityDataBaseHelper(context: Context): SQLiteOpenHelper(
    context, DATABASE_NAME,
    null, DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE)
        onCreate(db)
    }

}

