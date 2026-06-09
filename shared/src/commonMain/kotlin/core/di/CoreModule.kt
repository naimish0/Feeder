package core.di

import androidx.lifecycle.viewmodel.compose.viewModel
import core.dispatcher.DefaultDispatcherProvider
import core.network.HttpClientFactory
import core.security.SessionManager
import core.security.TokenStorage
import core.security.provideTokenStorage
import org.koin.dsl.module

val coreModule = module {
    single {
        DefaultDispatcherProvider()
    }
    single<TokenStorage> {
        provideTokenStorage()
    }
    single {
        SessionManager(get())
    }
    single {
        HttpClientFactory(tokenStorage = get())
    }
    single {
        get<HttpClientFactory>().create()
    }
}