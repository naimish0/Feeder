package feature_auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username_or_email: String,
    val password: String
)