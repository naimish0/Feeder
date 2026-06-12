package feature_auth.presentation.result

import kotlinx.serialization.Serializable

sealed interface LoginResult {
    data class UsernameUpdated(val value: String) : LoginResult
    data class PasswordUpdated(val value: String) : LoginResult
    data object LoginStarted : LoginResult
    data object LoginSuccess : LoginResult
    data class LoginFailed(val message: String) : LoginResult
    data object PasswordVisibilityToggled : LoginResult

}