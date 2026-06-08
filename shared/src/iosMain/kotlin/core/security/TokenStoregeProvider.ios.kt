package core.security

actual fun provideTokenStorage(): TokenStorage {
    return IosTokenStorage()
}