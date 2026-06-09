package feature_auth.data.remote.datasource

import feature_auth.data.remote.api.ResponseApi
import feature_auth.data.remote.dto.LoginRequest
import feature_auth.data.remote.dto.LoginResponse

class LoginResponseDataSource(private val responseApi: ResponseApi) {

    suspend fun getLoginResponse(request: LoginRequest): Result<LoginResponse> {
        return responseApi.getLoginResponse(request)
    }
}