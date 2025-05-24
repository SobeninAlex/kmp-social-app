package ru.sobeninalex.authorization.presentation.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
) {
    companion object {
        val Preview = LoginUiState(
            email = "",
            password = ""
        )
    }
}