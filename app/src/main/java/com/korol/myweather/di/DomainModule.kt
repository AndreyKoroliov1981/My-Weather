package com.korol.myweather.di

import com.korol.myweather.ui.city.City
import com.korol.myweather.ui.city.CityRepository
import com.korol.myweather.ui.details.DetailDataBase
import com.korol.myweather.ui.details.DetailsRepository
import com.korol.myweather.ui.search.SearchCity
import com.korol.myweather.ui.search.SearchRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideSearchCity(searchRepository : SearchRepository):SearchCity{
        return SearchCity(searchRepository = searchRepository)
    }

    @Provides
    fun provideCity(cityRepository : CityRepository):City{
        return City(cityRepository = cityRepository)
    }

    @Provides
    fun provideDetailDataBase(detailsRepository : DetailsRepository):DetailDataBase{
        return DetailDataBase(detailsRepository = detailsRepository)
    }

}