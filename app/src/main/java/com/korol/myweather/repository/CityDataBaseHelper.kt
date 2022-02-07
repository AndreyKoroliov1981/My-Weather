package com.korol.myweather.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.korol.myweather.repository.MyCityDataBaseNameClass.DATABASE_NAME
import com.korol.myweather.repository.MyCityDataBaseNameClass.DATABASE_VERSION
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class CityDataBaseHelper(val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(MyCityDataBaseNameClass.SQL_CREATE_TABLE)

        val mCSVfile = "cityBase.csv"
        val manager = context.assets
        var inStream: InputStream? = null
        try {
            inStream = manager.open(mCSVfile)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val buffer = BufferedReader(InputStreamReader(inStream))
        var line: String? = ""
        db!!.beginTransaction()
        try {
            line = buffer.readLine() //читаем "шапку" таблицы
            line = buffer.readLine() // читаем первую строчку таблицы
            while (line != null) {
                val colums = line.split(";")
                if (colums.size != 5) {
                    line = buffer.readLine()
                    continue
                }
                val cv = ContentValues()
                cv.put(MyCityDataBaseNameClass.COLUMN_NAME_CITY, colums[0].trim { it <= ' ' })
                cv.put(MyCityDataBaseNameClass.COLUMN_NAME_TYPE_REGION, colums[1].trim { it <= ' ' })
                cv.put(MyCityDataBaseNameClass.COLUMN_NAME_REGION, colums[2].trim { it <= ' ' })
                cv.put(MyCityDataBaseNameClass.COLUMN_NAME_LATITUDE, colums[3].trim { it <= ' ' })
                cv.put(MyCityDataBaseNameClass.COLUMN_NAME_LONGITUDE, colums[4].trim { it <= ' ' })
                db!!.insert(MyCityDataBaseNameClass.TABLE_NAME, null, cv)
                line = buffer.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        db!!.setTransactionSuccessful()
        db!!.endTransaction()

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(MyCityDataBaseNameClass.SQL_DELETE_TABLE)
        onCreate(db)
    }
}