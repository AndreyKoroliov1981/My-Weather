package com.korol.myweather.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.korol.myweather.ui.search.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityDataBase(context: Context): SearchRepository {
    val myDbHelper = CityDataBaseHelper(context)
    var db: SQLiteDatabase? = null


    override suspend fun readData(searchText: String): ArrayList<ListItem> = withContext(Dispatchers.IO) {
        val dataList = ArrayList<ListItem>()
        val selection = "${MyCityDataBaseNameClass.COLUMN_NAME_CITY} like ?"
        db = myDbHelper.writableDatabase
        val cursor = db?.query(
            MyCityDataBaseNameClass.TABLE_NAME,
            null,
            selection,
            arrayOf("%$searchText%"),
            null,
            null,
            null
        )

        while (cursor?.moveToNext()!!) {
            val dataCity =
                cursor.getStringOrNull(cursor.getColumnIndex(MyCityDataBaseNameClass.COLUMN_NAME_CITY))
            val dataTypeRegion =
                cursor.getStringOrNull(cursor.getColumnIndex(MyCityDataBaseNameClass.COLUMN_NAME_TYPE_REGION))
            val dataRegion =
                cursor.getStringOrNull(cursor.getColumnIndex(MyCityDataBaseNameClass.COLUMN_NAME_REGION))
            val dataLat =
                cursor.getStringOrNull(cursor.getColumnIndex(MyCityDataBaseNameClass.COLUMN_NAME_LATITUDE))
            val dataLon =
                cursor.getStringOrNull(cursor.getColumnIndex(MyCityDataBaseNameClass.COLUMN_NAME_LONGITUDE))
            val dataId = cursor.getIntOrNull(cursor.getColumnIndex(BaseColumns._ID))
            val item = ListItem()
            item.city = dataCity!!
            item.typeRegion = dataTypeRegion!!
            item.region = dataRegion!!
            item.latitude = dataLat!!
            item.longitude = dataLon!!
            item.id = dataId!!
            dataList.add(item)
        }

        cursor.close()
        myDbHelper.close()
        return@withContext dataList
    }   

}