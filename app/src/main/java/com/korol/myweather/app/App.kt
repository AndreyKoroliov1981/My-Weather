package com.korol.myweather.app

import android.app.Application
import com.korol.myweather.di.AppComponent
import com.korol.myweather.di.AppModule
import com.korol.myweather.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }
}