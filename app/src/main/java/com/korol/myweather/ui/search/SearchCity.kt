package com.korol.myweather.ui.search

import com.korol.myweather.repository.ListItem

class SearchCity(private val searchRepository:SearchRepository) {

    suspend fun readData(searchText:String): ArrayList<ListItem>{
        return searchRepository.readData(searchText)
    }
}