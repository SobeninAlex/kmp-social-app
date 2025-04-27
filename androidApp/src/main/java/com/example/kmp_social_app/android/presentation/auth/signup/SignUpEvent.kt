package com.example.kmp_social_app.android.presentation.auth.signup

sealed interface SignUpEvent {

    data class InputUsername(val username: String): SignUpEvent

    data class InputEmail(val email: String): SignUpEvent

    data class InputPassword(val password: String): SignUpEvent
}