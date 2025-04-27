package com.example.kmp_social_app.android.presentation.auth.signup

import androidx.compose.runtime.Immutable

@Immutable
data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = ""
) {
    companion object {
        val Preview = SignUpUiState(
            username = "",
            email = "",
            password = ""
        )
    }
}