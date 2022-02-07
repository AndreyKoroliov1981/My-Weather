package com.korol.myweather.di

import android.content.Context
import com.korol.myweather.ui.city.City
import com.korol.myweather.ui.city.CityViewModelFactory
import com.korol.myweather.ui.details.DetailDataBase
import com.korol.myweather.ui.details.DetailsViewModelFactory
import com.korol.myweather.ui.search.SearchCity
import com.korol.myweather.ui.search.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideSearchViewModelFactory(searchCity: SearchCity): SearchViewModelFactory {
        return SearchViewModelFactory(searchCity = searchCity)
    }

    @Provides
    fun provideCityViewModelFactory(city: City): CityViewModelFactory {
        return CityViewModelFactory(city = city)
    }

    @Provides
    fun provideDetailsViewModelFactory(detailDataBase : DetailDataBase
    ): DetailsViewModelFactory {
        return DetailsViewModelFactory(detailDataBase = detailDataBase)
    }

}