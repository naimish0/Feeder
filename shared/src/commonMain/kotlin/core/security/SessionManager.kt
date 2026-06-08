package core.security

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.supervisorScope

class SessionManager(private val tokenStorage: TokenStorage) {
    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Loading)
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()



    suspend fun initialize() {
        val token = tokenStorage.getToken()
        _sessionState.value =
            (if (token.isNullOrBlank()) SessionState.Unauthenticated else SessionState.Authenticated)
    }

    suspend fun login(token: String) {
        tokenStorage.saveToken(token)
        _sessionState.value = SessionState.Authenticated
    }

    suspend fun logout() {
        tokenStorage.clearToken()
        _sessionState.value = SessionState.Unauthenticated
    }

    suspend fun getToken(): String? {
        return tokenStorage.getToken()
    }

    suspend fun onSessionExpired() {
        logout()
    }
}