package feature_auth.di

import feature_auth.data.remote.api.LoginResponseApiImpl
import feature_auth.data.remote.api.ResponseApi
import feature_auth.data.remote.datasource.LoginResponseDataSource
import feature_auth.data.repository.LoginRepositoryImpl
import feature_auth.domain.repository.AuthRepository
import feature_auth.domain.usecase.LoginUseCase
import feature_auth.presentation.viewmodel.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    single<ResponseApi> {
        LoginResponseApiImpl(get())
    }
    single {
        LoginResponseDataSource(get())
    }
    single { LoginRepositoryImpl(get(), get()) }
    single<AuthRepository> {
        LoginRepositoryImpl(get(), get())
    }
    single {
        LoginUseCase(get())
    }
    viewModel {
        LoginViewModel(get())
    }
}