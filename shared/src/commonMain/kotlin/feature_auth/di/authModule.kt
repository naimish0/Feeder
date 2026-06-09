package feature_auth.di

import feature_auth.data.remote.api.LoginResponseApiImpl
import feature_auth.data.remote.api.ResponseApi
import feature_auth.data.remote.datasource.LoginResponseDataSource
import org.koin.dsl.module

val authModule = module {

    single<ResponseApi> {
        LoginResponseApiImpl(get())
    }
    single {
        LoginResponseDataSource(get())
    }
}