package core.security

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.get

private const val TOKEN_KEY = "auth_token"

@OptIn(ExperimentalSettingsImplementation::class)
class IosTokenStorage : TokenStorage {

    private val settings = KeychainSettings("auth")

    override suspend fun saveToken(token: String) {
        settings.putString(TOKEN_KEY, token)
    }

    override suspend fun getToken(): String? {
        return settings.getStringOrNull(TOKEN_KEY)
    }

    override suspend fun clearToken() {
        settings.remove(TOKEN_KEY)
    }
}