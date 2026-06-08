package core.network

import core.network.networkexception.UnauthorizedException
import core.security.SessionManager
import core.security.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.io.IOException
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val tokenStorage: TokenStorage
) {
    fun create(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    })
            }
            install(DefaultRequest) {
                url(NetworkConstants.BASE_URL)
                header("content-Type", "application/json")
                header("Accept", "application/json")
            }
            install(Auth) {
                bearer {
                    sendWithoutRequest { true }
                    loadTokens {
                        val token = tokenStorage.getToken()
                        return@loadTokens if (token == null) {
                            null
                        } else {
                            BearerTokens(accessToken = token, refreshToken = "")
                        }
                    }
                }
            }
            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, _ ->
                    if (cause is ClientRequestException && cause.response.status == HttpStatusCode.Unauthorized) {
                        throw UnauthorizedException()
                    }
                }
            }

            install(HttpRequestRetry) {
                maxRetries = 2
                retryIf { _, response ->
                    response.status.value >= 500
                }
                retryOnExceptionIf { builder, cause ->
                    cause is IOException
                }
                delay {
                    1000L
                }
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 30_000
                socketTimeoutMillis = 30_000
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }
}