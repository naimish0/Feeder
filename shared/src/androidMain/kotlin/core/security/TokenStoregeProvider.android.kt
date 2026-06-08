package core.security

import android.content.Context
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private object AndroidContextProvider : KoinComponent

actual fun provideTokenStorage(): TokenStorage {
    return AndroidTokenStorage(context = AndroidContextProvider.get<Context>())
}