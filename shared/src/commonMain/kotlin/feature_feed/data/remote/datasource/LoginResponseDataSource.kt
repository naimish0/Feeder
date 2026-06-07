package feature_feed.data.remote.datasource

import feature_feed.data.remote.api.ResponseApi
import feature_feed.data.remote.dto.LoginRequest
import feature_feed.data.remote.dto.LoginResponse

class LoginResponseDataSource(private val responseApi: ResponseApi) {

    suspend fun getLoginResponse(request: LoginRequest): Result<LoginResponse> {
        return responseApi.getLoginResponse(request)
    }
}