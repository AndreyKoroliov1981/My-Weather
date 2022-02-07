package com.korol.myweather.di

import android.content.Context
import com.korol.myweather.db.ViewCityDataBaseManager
import com.korol.myweather.repository.CityDataBase
import com.korol.myweather.ui.city.CityRepository
import com.korol.myweather.ui.details.DetailsRepository
import com.korol.myweather.ui.search.SearchRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideSearchRepository(context: Context): SearchRepository {
    return  CityDataBase(context = context)
    }

    @Provides
    fun provideCityRepository(context: Context): CityRepository {
        return  ViewCityDataBaseManager(context = context)
    }

    @Provides
    fun provideDetailsRepository(context: Context): DetailsRepository {
        return  ViewCityDataBaseManager(context = context)
    }

}