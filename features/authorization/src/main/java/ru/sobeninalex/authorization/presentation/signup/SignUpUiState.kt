package ru.sobeninalex.authorization.presentation.signup

import androidx.compose.runtime.Immutable

@Immutable
data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isAuthSuccess: Boolean = false,
) {
    companion object {
        val Preview = SignUpUiState(
            username = "",
            email = "",
            password = ""
        )
    }
}