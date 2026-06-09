package feature_feed.di

import feature_feed.data.remote.api.FeedApi
import feature_feed.data.remote.api.FeedApiImpl
import feature_feed.data.remote.datasource.FeedRemoteDataSource
import org.koin.dsl.module

val feedModule = module {
    single<FeedApi> {
        FeedApiImpl(get())
    }
    single {
        FeedRemoteDataSource(get())
    }
}