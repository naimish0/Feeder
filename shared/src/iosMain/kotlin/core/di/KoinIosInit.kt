package core.di

import feature_auth.di.authModule
import feature_feed.di.feedModule
import feature_post_detail.di.postDetailModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(appDeclaration: KoinApplication.() -> Unit) {
    startKoin {
        appDeclaration()
        modules(coreModule, authModule, feedModule, postDetailModule)
    }
}

// Called from iOS Swift entry point — iOSApp.swift
fun initKoinIos() {
    initKoin {
        modules(iosModule)
    }
}