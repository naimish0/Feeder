package feature_feed.di

import feature_feed.data.remote.api.FeedApi
import feature_feed.data.remote.api.FeedApiImpl
import feature_feed.data.remote.api.LoginResponseApiImpl
import feature_feed.data.remote.api.ResponseApi
import feature_feed.data.remote.datasource.FeedRemoteDataSource
import feature_feed.data.remote.datasource.LoginResponseDataSource
import org.koin.dsl.module

val feedModule = module {
    single<FeedApi> {
        FeedApiImpl(get())
    }
    single {
        FeedRemoteDataSource(get())
    }

    single<ResponseApi> {
        LoginResponseApiImpl(get())
    }
    single {
        LoginResponseDataSource(get())
    }
}