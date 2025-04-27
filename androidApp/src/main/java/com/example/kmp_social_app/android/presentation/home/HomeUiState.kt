package com.example.kmp_social_app.android.presentation.home

import androidx.compose.runtime.Immutable

@Immutable
data class HomeUiState(
    val email: String = "",
    val password: String = ""
) {
    companion object {
        val Preview = HomeUiState(
            email = "",
            password = ""
        )
    }
}