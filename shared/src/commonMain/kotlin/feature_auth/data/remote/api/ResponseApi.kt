package feature_auth.data.remote.api

import feature_auth.data.remote.dto.LoginRequest
import feature_auth.data.remote.dto.LoginResponse

interface ResponseApi {
    suspend fun getLoginResponse(request: LoginRequest): Result<LoginResponse>
}