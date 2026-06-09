package com.example.feeder

import android.app.Application
import core.di.initKoin
import feature_auth.data.remote.datasource.LoginResponseDataSource
import feature_auth.data.remote.dto.LoginRequest
import feature_feed.data.remote.datasource.FeedRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext

class FeederApp : Application() {
    val feedRemoteDataSource by inject<FeedRemoteDataSource>()
    override fun onCreate() {
        super.onCreate()
        println(": Feeder App onCreate")
        initKoin {
            androidContext(this@FeederApp)
        }
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                try {
                    println("Naimish: Feeder withContext")
//                    val loginResponse = feedRemoteDataSource.getLoginResponse(
//                        LoginRequest(
//                            "naimish", "naimis"
//                        )
//                    )
//                    loginResponse.fold(onSuccess = {it->
//                        println("Login Response: ${it.jwt}")
//                    }, onFailure = {it->
//                        println("Login errror: ${it.message}")
//                    })
                    val feed = feedRemoteDataSource.getHotPosts()
                    println("Naimish: Login Response: $feed")


                } catch (e: Exception) {
                    println("FeederApp exception: $e")
                }

            }
        }

    }
}