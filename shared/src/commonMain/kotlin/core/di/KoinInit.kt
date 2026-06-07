package core.di

import feature_feed.di.feedModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration? = null) {
    println("Koin Started")
    startKoin {
        appDeclaration?.invoke(this)
        modules(coreModule, feedModule)
    }
}