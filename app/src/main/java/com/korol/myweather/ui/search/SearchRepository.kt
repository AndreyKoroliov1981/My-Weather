package com.korol.myweather.ui.search

import com.korol.myweather.repository.ListItem

interface SearchRepository {
    suspend fun readData(searchText: String): ArrayList<ListItem>
}