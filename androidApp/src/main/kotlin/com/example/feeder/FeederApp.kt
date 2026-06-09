package com.example.feeder

import android.app.Application
import core.di.androidModule
import core.di.initKoin
import org.koin.android.ext.koin.androidContext

class FeederApp : Application() {
    override fun onCreate() {
        super.onCreate()
        println(": Feeder App onCreate")
        initKoin {
            androidContext(this@FeederApp)
            modules(androidModule)
        }
    }
}