package feature_auth.presentation.action

sealed interface LoginAction {
    data object SubmitLogin : LoginAction
    data object TogglePasswordVisibility : LoginAction
    data class UpdateUserName(val userName: String) : LoginAction
    data class UpdatePassword(val password: String) : LoginAction
}
