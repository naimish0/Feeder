package feature_auth.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import feature_auth.presentation.intent.LoginIntent
import feature_auth.presentation.state.LoginState

@Composable
fun LoginScreen(
    state: LoginState, onIntent: (LoginIntent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { snackbarHostState.showSnackbar(it) }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(paddingValues).padding(horizontal = 32.dp).imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(Modifier.height(48.dp))

            // ── Header ────────────────────────────────────────────────
            Text(
                text = "Feeder",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Sign in to your Lemmy account",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(48.dp))



            // ── Username ──────────────────────────────────────────────
            OutlinedTextField(
                value = state.username,
                onValueChange = { onIntent(LoginIntent.UpdateUserName(it)) },
                label = { Text("Username or Email") },
                singleLine = true,
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                )
            )

            Spacer(Modifier.height(16.dp))

            // ── Password ──────────────────────────────────────────────
            OutlinedTextField(
                value = state.password,
                onValueChange = { onIntent(LoginIntent.UpdatePassword(it)) },
                label = { Text("Password") },
                singleLine = true,
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onIntent(LoginIntent.LoginClicked) }),
                trailingIcon = {
                    TextButton(
                        onClick = { onIntent(LoginIntent.TogglePasswordVisibility) }) {
                        Text(
                            text = if (state.isPasswordVisible) "Hide" else "Show",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                })

            Spacer(Modifier.height(32.dp))

            // ── Sign In Button ────────────────────────────────────────
            Button(
                onClick = { onIntent(LoginIntent.LoginClicked) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = "Sign In", style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Spacer(Modifier.height(48.dp))
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(state = LoginState(), onIntent = {})
    }
}

@Preview
@Composable
private fun LoginScreenLoadingPreview() {
    MaterialTheme {
        LoginScreen(
            state = LoginState(username = "alice", password = "hunter2", isLoading = true),
            onIntent = {})
    }
}