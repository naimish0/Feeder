package feature_auth.presentation.intent

sealed interface LoginIntent {
    data object LoginClicked : LoginIntent
    data object TogglePasswordVisibility : LoginIntent
    data class UpdateUserName(val userName: String) : LoginIntent
    data class UpdatePassword(val password: String) : LoginIntent
}