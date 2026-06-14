package core.di

import feature_auth.di.authModule
import feature_feed.di.feedModule
import feature_post_detail.di.postDetailModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration? = null) {
    println("Koin Started")
    startKoin {
        appDeclaration?.invoke(this)
        modules(coreModule, feedModule, authModule, postDetailModule)
    }
}