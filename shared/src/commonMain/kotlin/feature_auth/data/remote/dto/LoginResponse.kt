package feature_auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val jwt: String? = null,
    val success: Boolean? = null,
    val registration_created: Boolean? = null,
    val verify_email_sent: Boolean? = null,
    val error: String? = null
)