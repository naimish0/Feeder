package feature_auth.data.remote.api

import core.network.Endpoints
import core.network.NetworkConstants
import feature_auth.data.remote.dto.LoginRequest
import feature_auth.data.remote.dto.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LoginResponseApiImpl(val client: HttpClient) : ResponseApi {
    override suspend fun getLoginResponse(request: LoginRequest): Result<LoginResponse> {
        return runCatching {
            client.post(NetworkConstants.BASE_URL + Endpoints.Auth.LOGIN) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<LoginResponse>()
        }
    }
}