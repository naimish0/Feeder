package com.example.feeder

import android.app.Application
import core.di.initKoin
import feature_feed.data.remote.datasource.FeedRemoteDataSource
import feature_feed.data.remote.datasource.LoginResponseDataSource
import feature_feed.data.remote.dto.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
class FeederApp: Application() {
    val feedRemoteDataSource by inject<LoginResponseDataSource>()
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@FeederApp)
        }
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                try {
                    val loginResponse = feedRemoteDataSource.getLoginResponse(LoginRequest("naimish", "naimish"))
                    loginResponse.fold(onSuccess = {
                        println("Login Response: $loginResponse")
                    }, onFailure = {

                    })

                } catch (e: Exception) {
                    println("FeederApp exception: $e")
                }

            }
        }

    }
}