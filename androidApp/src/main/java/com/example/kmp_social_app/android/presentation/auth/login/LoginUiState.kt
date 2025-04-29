package com.example.kmp_social_app.android.presentation.auth.login

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