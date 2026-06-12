package feature_auth.data.repository

import core.architecture.BaseRepository
import core.security.SessionManager
import feature_auth.data.remote.datasource.LoginResponseDataSource
import feature_auth.data.remote.dto.LoginRequest
import feature_auth.domain.repository.AuthRepository

class LoginRepositoryImpl(
    private val loginResponseDataSource: LoginResponseDataSource,
    private val sessionManager: SessionManager
) : AuthRepository, BaseRepository(sessionManager = sessionManager) {
    override suspend fun login(
        username: String,
        password: String
    ): Result<Unit> {
        return execute {
            val request = LoginRequest(
                userName = username,
                password = password
            )
            val response = loginResponseDataSource.getLoginResponse(request)
            val token = response.getOrNull()?.jwt ?: throw Exception("Authentication failed: no token received")
            sessionManager.login(token)
        }
    }
}