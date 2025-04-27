package com.example.kmp_social_app.android.presentation.auth.login

sealed interface LoginEvent {

    data class InputEmail(val email: String): LoginEvent

    data class InputPassword(val password: String): LoginEvent
}