package feature_auth.presentation.viewmodel

import core.architecture.BaseViewModel
import feature_auth.domain.usecase.LoginUseCase
import feature_auth.presentation.action.LoginAction
import feature_auth.presentation.intent.LoginIntent
import feature_auth.presentation.navigation.LoginNavigation
import feature_auth.presentation.result.LoginResult
import feature_auth.presentation.state.LoginState

class LoginViewModel(private val loginUseCase: LoginUseCase) :
    BaseViewModel<LoginIntent, LoginAction, LoginResult, LoginState, LoginNavigation>(
        LoginState()
    ) {

    override fun toAction(intent: LoginIntent): LoginAction {
        return when (intent) {
            LoginIntent.LoginClicked -> LoginAction.SubmitLogin
            LoginIntent.TogglePasswordVisibility -> LoginAction.TogglePasswordVisibility
            is LoginIntent.UpdatePassword -> LoginAction.UpdatePassword(intent.password)
            is LoginIntent.UpdateUserName -> LoginAction.UpdateUserName(intent.userName)
        }
    }

    override suspend fun handleAction(action: LoginAction) {
        when (action) {
            LoginAction.SubmitLogin -> {
                if (currentState.isLoading) return
                if (currentState.username.isBlank() || currentState.password.isBlank()) {
                    emitResult(LoginResult.LoginFailed("Username and password cannot be empty"))
                    return
                }
                emitResult(LoginResult.LoginStarted)
                loginUseCase(currentState.username.trim(), currentState.password).onSuccess {
                    emitResult(LoginResult.LoginSuccess)
                }.onFailure { error ->
                    emitResult(LoginResult.LoginFailed(error.message ?: "Login failed. Please try again."))
                }
            }

            LoginAction.TogglePasswordVisibility -> {
                emitResult(LoginResult.PasswordVisibilityToggled)
            }

            is LoginAction.UpdatePassword -> {
                emitResult(LoginResult.PasswordUpdated(action.password))
            }

            is LoginAction.UpdateUserName -> {
                emitResult(LoginResult.UsernameUpdated(action.userName))
            }
        }
    }

    override fun reduce(
        state: LoginState, result: LoginResult
    ): LoginState {
        return when (result) {
            is LoginResult.LoginFailed -> state.copy(isLoading = false, errorMessage = result.message)
            LoginResult.LoginStarted -> state.copy(isLoading = true, errorMessage = null)
            LoginResult.LoginSuccess -> state.copy(isLoading = false, errorMessage = null)
            LoginResult.PasswordVisibilityToggled -> state.copy(isPasswordVisible = !state.isPasswordVisible)
            is LoginResult.PasswordUpdated -> state.copy(password = result.value)
            is LoginResult.UsernameUpdated -> state.copy(username = result.value)
        }
    }

}