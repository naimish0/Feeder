package feature_feed.data.remote.api

import feature_feed.data.remote.dto.LoginRequest
import feature_feed.data.remote.dto.LoginResponse

interface ResponseApi {
    suspend fun getLoginResponse(request: LoginRequest): Result<LoginResponse>
}