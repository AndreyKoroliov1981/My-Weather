package com.korol.myweather.di

import com.korol.myweather.MainActivity
import com.korol.myweather.ui.city.CityFragment
import com.korol.myweather.ui.details.DetailsFragment
import com.korol.myweather.ui.search.SearchFragment
import dagger.Component

@Component(modules = [AppModule::class,DomainModule::class,DataModule::class])
interface AppComponent {

    fun injectSearchFragment(searchFragment: SearchFragment)

    fun injectCityFragment(cityFragment: CityFragment)

    fun injectDetailsFragment(detailsFragment: DetailsFragment)

}